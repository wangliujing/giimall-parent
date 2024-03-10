/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.giimall.common.custom.consul;

import com.ecwid.consul.v1.ConsulClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.discovery.TtlScheduler;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;


/**
 * 重新定义TtlScheduler是为了解决，consul宕机重启后服务无法自动重新注册问题
 * @author wangLiuJing
 * @date 2022/09/23
 */
@Slf4j
public class CustomTtlScheduler extends TtlScheduler {

	private final Map<String, ScheduledFuture> serviceHeartbeats = new ConcurrentHashMap<>();

	private final TaskScheduler scheduler = new ConcurrentTaskScheduler(
			Executors.newSingleThreadScheduledExecutor());

	private HeartbeatProperties configuration;

	private ConsulClient client;

	private ConsulServiceRegistry consulServiceRegistry;

	private ConsulRegistration consulRegistration;

	public CustomTtlScheduler(HeartbeatProperties configuration, ConsulClient client,
							  ConsulRegistration consulRegistration, ConsulServiceRegistry consulServiceRegistry) {
		super(configuration, client);
		this.configuration = configuration;
		this.client = client;
		this.consulRegistration = consulRegistration;
		this.consulServiceRegistry = consulServiceRegistry;
	}

	/**
	 * Add a service to the checks loop.
	 * @param instanceId instance id
	 */
	@Override
	public void add(String instanceId) {
		ScheduledFuture task = this.scheduler.scheduleAtFixedRate(
				new ConsulHeartbeatTask(instanceId), 20000);
		ScheduledFuture previousTask = this.serviceHeartbeats.put(instanceId, task);
		if (previousTask != null) {
			previousTask.cancel(true);
		}
	}

	@Override
	public void remove(String instanceId) {
		ScheduledFuture task = this.serviceHeartbeats.get(instanceId);
		if (task != null) {
			task.cancel(true);
		}
		this.serviceHeartbeats.remove(instanceId);
	}

	private class ConsulHeartbeatTask implements Runnable {

		private String checkId;

		ConsulHeartbeatTask(String serviceId) {
			this.checkId = serviceId;
			if (!this.checkId.startsWith("service:")) {
				this.checkId = "service:" + this.checkId;
			}
		}

		@Override
		public void run() {
			try {
				CustomTtlScheduler.this.client.agentCheckPass(this.checkId);
				if (log.isDebugEnabled()) {
					log.debug("Sending consul heartbeat for: " + this.checkId);
				}
			} catch (Exception exception) {
				log.info("重新注册服务");
				consulServiceRegistry.register(consulRegistration);
			}
		}

	}

}

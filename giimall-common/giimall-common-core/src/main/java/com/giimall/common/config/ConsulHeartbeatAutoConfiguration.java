package com.giimall.common.config;

import com.ecwid.consul.v1.ConsulClient;
import com.giimall.common.custom.consul.CustomTtlScheduler;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.consul.ConditionalOnConsulEnabled;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClientConfiguration;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * Auto configuration for the heartbeat.
 *
 * @author Tim Ysewyn
 */
@SpringBootConfiguration
@ConditionalOnConsulEnabled
@ConditionalOnProperty("spring.cloud.consul.discovery.heartbeat.enabled")
@ConditionalOnDiscoveryEnabled
@AutoConfigureBefore({ ConsulServiceRegistryAutoConfiguration.class })
@AutoConfigureAfter({ ConsulDiscoveryClientConfiguration.class })
public class ConsulHeartbeatAutoConfiguration {

	@Bean
	public CustomTtlScheduler ttlScheduler(HeartbeatProperties heartbeatProperties,
										   ConsulClient consulClient,
										   ConsulRegistration consulRegistration,
										   @Lazy ConsulServiceRegistry consulServiceRegistry) {
		return new CustomTtlScheduler(heartbeatProperties, consulClient, consulRegistration, consulServiceRegistry);
	}

}

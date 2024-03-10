/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.util.lock;

import com.giimall.common.factory.NamedThreadFactory;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

/**
 * Jvm级别请求限流器
 * 该限流器只适合用于jvm级别单机限流，不适用分布式场景
 *
 * @author wangLiuJing
 * @version Id: JvmRequestCurrentLimiter, v1.0.0 2022年10月08日 11:04 wangLiuJing Exp $ 
 */
@Slf4j
public class JvmRequestCurrentLimiter implements RequestCurrentLimiter, Runnable {

	private ExecutorService releaseExecutor = newSingleThreadScheduledExecutor(
			new NamedThreadFactory("delay-release-lock", true));

	/**
	 * 延迟队列主要与信号量配合使用进行限流请求
	 */
	private DelayQueue<ReleasesLockTask> delayQueue = new DelayQueue();

	/**
	 * 信号量,用于请求限流
	 */
	private Semaphore semaphore;

	private long time;

	private TimeUnit timeUnit;

	/**
	 * @param permits
	 * @param time
	 * @param timeUnit
	 */
	public JvmRequestCurrentLimiter(int permits, long time, TimeUnit timeUnit) {
		semaphore = new Semaphore(permits);
		this.time = time;
		this.timeUnit = timeUnit;
		releaseExecutor.submit(this);

	}


	@Override
	@SneakyThrows
	public void lock() {
		semaphore.acquire();
		delayQueue.put(new ReleasesLockTask(time, timeUnit));
	}


	@Override
	public void run() {
		while (true) {
			try {
				ReleasesLockTask releasesLockTask = delayQueue.take();
				releasesLockTask.run();
			} catch (InterruptedException e) {
				log.error("延迟解锁错误", e);
			}
		}
	}


	@Data
	class ReleasesLockTask implements Runnable, Delayed {

		// 毫秒
		private Long scheduleTime;

		public ReleasesLockTask(long delay, TimeUnit unit) {
			this.scheduleTime = System.currentTimeMillis() + (delay > 0 ? unit.toMillis(delay) : 0);
		}

		@Override
		public void run() {
			semaphore.release();
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(scheduleTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}

		/**
		 * 升序排列
		 * @param o
		 * @return int
		 */
		@Override
		public int compareTo(Delayed o) {
			return (int) (this.scheduleTime - ((ReleasesLockTask) o).getScheduleTime());
		}
	}
}

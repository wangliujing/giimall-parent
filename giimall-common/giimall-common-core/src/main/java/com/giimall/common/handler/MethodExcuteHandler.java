package com.giimall.common.handler;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 方法执行器
 *
 * @author wangLiuJing
 * Created on 2019/11/15
 */
@Slf4j
public class MethodExcuteHandler {

	/**
	 * 在指定时间内执行方法
	 *
	 * @param callable of type Callable<T>
	 * @param time     of type long
	 * @param timeUnit of type TimeUnit
	 * @return Object
	 * @author wangLiuJing
	 * Created on 2019/11/15
	 */
	public static <T> T callMethod(Callable<T> callable, long time, TimeUnit timeUnit) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		FutureTask<T> futureTask = new FutureTask<>(callable);
		executorService.execute(futureTask);
		try {
			return futureTask.get(time, timeUnit);
		} catch (InterruptedException e) {
			log.info("方法执行中断", e);
		} catch (ExecutionException e) {
			log.info("执行异常", e);
		} catch (TimeoutException e) {
			log.info("超时异常", e);
		}
		return null;
	}
}

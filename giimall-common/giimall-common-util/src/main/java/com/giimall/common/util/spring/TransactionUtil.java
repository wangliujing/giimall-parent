/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.util.spring;

import com.giimall.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 事物工具类
 *
 * @author wangLiuJing
 * @version Id: TransactionUtil, v1.0.0 2022年04月07日 16:08 wangLiuJing Exp $ 
 */
@Slf4j
public class TransactionUtil {

	/**
	 * 提交事物后执行任务
	 *
	 * @param task 任务
	 */
	public static void afterCommit(final Runnable task) {
		if(TransactionSynchronizationManager.isActualTransactionActive()) {
			TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
				@Override
				public void afterCommit() {
					log.debug("事物已经提交，准备执行相关任务");
					task.run();
					log.debug("相关任务执行完成");
				}
			});
			return;
		}
		throw new CommonException("com.giimall.common.util.spring.TransactionUtil.afterCommit 必须在事物中执行");
	}

	/**
	 * 提交事物后异步执行任务
	 *
	 * @param task 任务
	 */
	public static void asyncAfterCommit(final Runnable task) {
		if(TransactionSynchronizationManager.isActualTransactionActive()) {
			TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
				@Override
				public void afterCommit() {
					log.debug("事物已经提交，准备执行相关任务");
					new Thread(task).start();
				}
			});
			return;
		}
		throw new CommonException("com.giimall.common.util.spring.TransactionUtil.asyncAfterCommit 必须在事物中执行");
	}
}

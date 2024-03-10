/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.util.lock;

/**
 *
 * 请求限流器
 * 主要用于调用第三方进行主动限流
 * @author wangLiuJing
 * @version Id: CurrentLimiter, v1.0.0 2022年10月08日 11:07 wangLiuJing Exp $ 
 */
public interface RequestCurrentLimiter {

	/**
	 * 获取锁
	 */
	void lock();
}

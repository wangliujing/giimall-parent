/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.privilege.constant;

/**
 * 平台权限
 *
 * @author wangLiuJing
 * @version Id: Platform, v1.0.0 2022年07月13日 11:48 wangLiuJing Exp $
 * @date 2022/07/13
 */
public enum AuthCode {
	MERCHANT(1 << 0),
	PLATFORM(1 << 1);
	private int value;

	private AuthCode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

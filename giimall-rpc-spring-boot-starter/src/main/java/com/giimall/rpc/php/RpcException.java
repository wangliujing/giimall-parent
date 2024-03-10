/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.rpc.php;

import lombok.Data;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: RpcException, v1.0.0 2022年07月12日 16:07 wangLiuJing Exp $ 
 */
@Data
public class RpcException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code;

	public RpcException(String message, int code) {
		super(message);
		this.code = code;
	}

	@Override
	public String toString() {
		return String.format("%s:%s:%s", code, this.getClass().getName(), this.getMessage());
	}
}

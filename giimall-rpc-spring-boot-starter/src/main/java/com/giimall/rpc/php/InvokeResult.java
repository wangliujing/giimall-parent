/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.rpc.php;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.giimall.common.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: InvokeResult, v1.0.0 2022年04月21日 14:17 wangLiuJing Exp $ 
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class InvokeResult {

	private Long id;

	private String jsonrpc;

	private Object result;

	private Error error;

	public Object getResult() {
		if(error != null) {
			RpcException rpcException = new RpcException(error.getMessage(), error.getCode());
			log.error(rpcException.toString());
			throw rpcException;
		}
		return result;
	}

	public Detail getErrorDetail(){
		return error.getData();
	}

	@Override
	public String toString() {
		if(result == null) {
			return null;
		}

		if(result instanceof List || result instanceof Map) {
			return JsonUtil.objectToJson(result);
		}
		return result.toString();
	}

	@Data
	public static class Error {

		private Integer code;

		private String message;

		private Detail data;
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Detail {

		private Integer code;

		private String message;

		@JsonProperty("class")
		private String clazz;
	}
}

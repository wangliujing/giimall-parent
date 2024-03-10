/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 请求上下文，网关鉴权通过后会向应用透传此信息
 *
 * {
 *     "x-request-context": {
 *         "acceptCurrency": "",
 *         "acceptLanguage": "zh_CN",
 *         "clientIp": "127.0.0.1",
 *         "isAdmin": true,
 *         "loginId": "13",
 *         "requestClient": "seller",
 *         "shopId": "145783563507302424",
 *         "shopSettings": "{"status":true,"defaultLanguageMode":2,"mainCurrency":{"currencyId":162,"code":"CNY","symbol":"￥","decimalLength":2},"mainLanguage":"zh-CN","language":["zh-CN"],"morelanguage":["zh-CN","en","KO"],"useMultipleCurrency":1,"useFixedExchangeRate":1,"currencyDisplayWay":1,"defaultDisplayWay":2,"currencyList":[],"timezone":"America\/Scoresbysund","plugins":[1002]}",
 *         "shopTimezone": "America/Scoresbysund",
 *         "userAgent": "pc",
 *         "userInfo": {
 *             "email": "gaojiande126@126.com",
 *             "status": "1",
 *             "userId": "13",
 *             "userName": "么么哒么么哒"
 *         }
 *     },
 *     "x-request-id": "d9aeb84f90bd6bef"
 * }
 *
 * @author wangLiuJing
 * @version Id: RequestContext, v1.0.0 2022年03月25日 16:15 wangLiuJing Exp $ 
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestContext implements Serializable {

	@JsonProperty("x-request-context")
	private XReqeustContext xRequestContext;

	@JsonProperty("x-request-header")
	private Map<String, String> xRequestHeader;

	/** 请求id */
	@JsonProperty("x-request-id")
	private String xRequestId;

	public static void main(String[] args) {
		System.out.println("ResolveLoginUserFilter获取到请求体：\n{}");
	}
}

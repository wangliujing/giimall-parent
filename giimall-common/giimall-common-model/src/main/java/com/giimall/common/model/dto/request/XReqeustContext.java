/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.giimall.common.constant.SymbolConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求
 *
 * @author wangLiuJing
 * @version Id: XReqeustContext, v1.0.0 2022年03月25日 16:07 wangLiuJing Exp $ 
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class XReqeustContext implements Serializable {

	//商家端
    public static final String REQUEST_CLIENT_SELLER = "seller";
	//买家端
	public static final String REQUEST_CLIENT_BUYER = "buyer";
	//平台端
	public static final String REQUEST_CLIENT_ADMIN = "admin";

	/** 接受货币 */
	private String acceptCurrency = SymbolConstant.EMPTY_STRING;

	/** 接受语言 */
	private String acceptLanguage = SymbolConstant.EMPTY_STRING;

	/** 客户端IP */
	private String clientIp = SymbolConstant.EMPTY_STRING;

	/** 是否是管理员 */
	private Boolean isAdmin;

	/** 登录id */
	private String loginId = SymbolConstant.EMPTY_STRING;

	/** 请求客户端 */
	private String requestClient = SymbolConstant.EMPTY_STRING;

	/** 商店id */
	private String shopId = SymbolConstant.EMPTY_STRING;

	/** 商店设置 */
	private String shopSettings = SymbolConstant.EMPTY_JSON;

	/** 商店时区 */
	private String shopTimezone = SymbolConstant.EMPTY_STRING;

	/** 用户代理 pc*/
	private String userAgent = SymbolConstant.EMPTY_STRING;

	/** 用户信息 */
	private UserInfo userInfo;

	/** Erp信息 */
	private ErpInfo erpInfo;

	private String userRegion = SymbolConstant.EMPTY_STRING;
}

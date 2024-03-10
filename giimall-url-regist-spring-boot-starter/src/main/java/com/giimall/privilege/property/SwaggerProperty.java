/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.privilege.property;

import com.giimall.privilege.constant.AuthCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: SwaggerProperty, v1.0.0 2022年07月12日 11:54 wangLiuJing Exp $ 
 */
@Data
@ConfigurationProperties(prefix = "knife4j")
@Slf4j
public class SwaggerProperty {

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 项目根路径
	 */
	private String basePath;
	/**
	 * 鉴权码
	 */
	private AuthCode authCode;

	public AuthCode getAuthCode() {
		if(authCode == null) {
			log.warn("authCode参数为空");
		}
		return authCode;
	}
}

/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求体上下文
 *
 * @author wangLiuJing
 * @version Id: Context, v1.0.0 2022年03月28日 19:21 wangLiuJing Exp $ 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RequestBodyContext implements Serializable {

	private RequestContext context;
}

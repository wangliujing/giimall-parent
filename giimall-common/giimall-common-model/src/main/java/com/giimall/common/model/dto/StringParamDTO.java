/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 字符串参数传输类
 *
 * @author wangLiuJing
 * @version Id: StringDTO, v1.0.0 2022年06月27日 15:59 wangLiuJing Exp $ 
 */
@Data
public class StringParamDTO {

	@NotBlank(message = "参数不能为空")
	protected String param;
}

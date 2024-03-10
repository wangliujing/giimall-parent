/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 下拉框公用视图类
 *
 * @author wangLiuJing
 * @version Id: SelectVO, v1.0.0 2022年06月17日 15:45 wangLiuJing Exp $ 
 */
@Data
public class SelectVO implements Serializable {

	private Long id;

	private String name;
}

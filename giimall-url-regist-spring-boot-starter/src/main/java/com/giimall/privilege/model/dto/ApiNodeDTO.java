/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.privilege.model.dto;

import com.giimall.common.constant.SymbolConstant;
import com.giimall.common.util.StringUtil;
import lombok.Data;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: ApiNode, v1.0.0 2022年07月12日 16:32 wangLiuJing Exp $ 
 */
@Data
public class ApiNodeDTO {

	private String parentUrl;

	private String url;

	private String alias;

	private Integer level;

	private Integer authCode;

	private Integer isExternal = 0;

	public ApiNodeDTO(String alias, String parentUrl, String url, Integer level, Integer authCode) {
		this.alias = alias;
		this.parentUrl = StringUtil.isEmpty(parentUrl) ? SymbolConstant.EMPTY_STRING: parentUrl;
		this.url = url;
		this.level = level;
		this.authCode = authCode;
		// 最后一级必须是1列表才能显示，不知道Php端是什么逻辑
		if(level == 3) {
			isExternal = 1;
		}
	}
}

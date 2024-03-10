/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 商店设置
 *
 * @author wangLiuJing
 * @version Id: ShopSettings, v1.0.0 2022年03月25日 17:16 wangLiuJing Exp $ 
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopSettings {

	/** 货币显示方式 */
	private Integer currencyDisplayWay;

	/** 货币列表 */
	private List<Currency> currencyList;

	/** 默认显示方式 */
	private Integer defaultDisplayWay;

	/** 默认语言模式 */
	private Integer defaultLanguageMode;

	/** 语言 */
	private List<String> language;

	/** 主要货币 */
	private MainCurrency mainCurrency;

	/** 主语言 */
	private String mainLanguage;

	/** 更多语言 */
	private List<String> morelanguage;

	/** 插件 */
	private List<Long> plugins;

	/** 状态 */
	private Boolean status;

	/** 时区 */
	private String timezone;

	/** 使用固定汇率 */
	private Integer useFixedExchangeRate;

	/** 使用多种货币 */
	private Integer useMultipleCurrency;

	/**
	 * 主要货币
	 *
	 * @author wangLiuJing
	 * @date 2022/03/25
	 */
	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MainCurrency {

		/** 货币id */
		private Long currencyId;

		/** 代码 */
		private String code;

		/** 小数长度 */
		private Integer decimalLength;

		/** 符号 */
		private String symbol;
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Currency {
		/** 代码 */
		private String code;
		/** 小数长度 */
		private Integer decimalLength;
		/** 符号 */
		private String symbol;

		private Integer rate;
	}
}

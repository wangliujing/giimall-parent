/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.dubbo.config;

import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: DubboConfig, v1.0.0 2022年05月10日 13:49 wangLiuJing Exp $ 
 */
@SpringBootConfiguration
public class GiimallDubboAutoConfig {

	@Bean
	@ConditionalOnMissingBean
	public ProviderConfig providerConfig() {
		ProviderConfig providerConfig = new ProviderConfig();
		providerConfig.setFilter("-exception");
		providerConfig.setValidation("true");
		return providerConfig;
	}

	@Bean
	@ConditionalOnMissingBean
	public ConsumerConfig consumerConfig() {
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setValidation("true");
		return consumerConfig;
	}

}

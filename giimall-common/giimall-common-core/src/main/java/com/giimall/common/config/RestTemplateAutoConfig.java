package com.giimall.common.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * RestTemplate 自动配置类
 *
 * @author wangLiuJing
 * Created on 2020/2/12
 */
@SpringBootConfiguration
@ConditionalOnClass({RestTemplate.class, RestTemplateBuilder.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class RestTemplateAutoConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		RestTemplate restTemplate = builder.build();
		// 设置restTemplate远程调用时候，不让报错，正确返回数据
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
			}
		});
		return restTemplate;
	}

}

package com.giimall.common.config;

import com.giimall.common.exception.handler.DefaultExceptionHandler;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * 全局异常处理器
 *
 * @author wangLiuJing
 * Created on 2020/4/21
 */
@SpringBootConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class GlobalExceptionHandlerAutoConfig {

	@Bean
	@ConditionalOnMissingClass("com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect")
	public DefaultExceptionHandler globalExceptionHandler() {
		return new DefaultExceptionHandler();
	}

/*	@Bean
	@ConditionalOnClass(name = "com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect")
	public SentinelExceptionHandler sentinelGlobalExceptionHandler(){
		return new SentinelExceptionHandler();
	}*/
}

package com.giimall.common.config;

import com.giimall.common.filter.ResolveRequestContextFilter;
import com.giimall.common.filter.ThreadLocalClearFilter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Api过滤器配置类,主要用于指定过滤器执行顺序
 *
 * @author wangLiuJing
 * Created on 2021/12/8
 */
@SpringBootConfiguration
public class CommonFilterAutoConfig {

	public static final String URL_PATTERN = "/*";

	@Bean
	public FilterRegistrationBean threadLocalClearFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new ThreadLocalClearFilter());
		filterRegistrationBean.addUrlPatterns(URL_PATTERN);
		filterRegistrationBean.setName("threadLocalClearFilter");
		filterRegistrationBean.setOrder(Integer.MIN_VALUE);
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean resolveLoginUserFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new ResolveRequestContextFilter());
		filterRegistrationBean.addUrlPatterns(URL_PATTERN);
		filterRegistrationBean.setName("resolveLoginUserFilter");
		filterRegistrationBean.setOrder(Integer.MIN_VALUE + 1);
		return filterRegistrationBean;
	}
}

package com.giimall.common.initialize;

import com.giimall.common.processor.GiimallBeanDefinitionRegistryPostProcessor;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * spring 容器初始化之前执行，方便对框架进行扩展
 *
 * @author wangLiuJing
 * Created on 2022/3/9
 */
public class GiimallApplicationContextInitializer implements ApplicationContextInitializer {

	@Override
	public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
		if(configurableApplicationContext instanceof AnnotationConfigServletWebServerApplicationContext) {
			configurableApplicationContext.addBeanFactoryPostProcessor(new GiimallBeanDefinitionRegistryPostProcessor());
		}
	}

}

/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.processor;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * MillisecondToSecondBeanPostProcessor 注册器
 *
 * @author wangLiuJing
 * @version Id: MyImportBeanDefinitionRegistrar, v1.0.0 2022年04月13日 16:27 wangLiuJing Exp $ 
 */
public class MillisecondToSecondImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory) registry;
		beanFactory.addBeanPostProcessor(new MillisecondToSecondBeanPostProcessor());
	}

}

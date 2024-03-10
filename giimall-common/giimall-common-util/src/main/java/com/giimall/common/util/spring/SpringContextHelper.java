package com.giimall.common.util.spring;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

/**
 * Spring 容器工具类
 *
 * @author wangLiuJing
 * Created on 2019/10/20
 */
public class SpringContextHelper implements ApplicationContextAware {

	/**
	 * Spring应用上下文环境
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 获取applicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 通过注解获取
	 *
	 * @param annotationClass 注解
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getBeanWithAnnotation(Class<? extends Annotation> annotationClass) {
		if(applicationContext == null){
			return null;
		}
		return applicationContext.getBeansWithAnnotation(annotationClass);
	}

	/**
	 * 通过类获取
	 *
	 * @param clazz 注入的类
	 * @param <T>   返回类型
	 * @return 返回这个bean
	 * @throws BeansException bean异常
	 */
	public static <T> T getBean(Class<T> clazz) throws BeansException {
		if(applicationContext == null){
			return null;
		}
		return applicationContext.getBean(clazz);
	}

	/**
	 * 通过类获取
	 *
	 * @param clazz 注入的类
	 * @param <T>   返回类型
	 * @return Collection<T>返回这个bean的集合
	 * @throws BeansException bean异常
	 */
	public static <T> Collection<T> getBeans(Class<T> clazz) throws BeansException {
		if(applicationContext == null){
			return null;
		}
		Map<String, T> beansOfType = applicationContext.getBeansOfType(clazz);
		return beansOfType.values();
	}

	/**
	 * 通过名字获取
	 *
	 * @param name 名字
	 * @param <T>  返回类型
	 * @return 返回这个bean
	 * @throws BeansException bean异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		if(applicationContext == null){
			return null;
		}
		return (T) applicationContext.getBean(name);
	}

	/***
	 * 从配置文件获取
	 * @param key
	 * @return
	 */
	public static String getApplicationProValue(String key) {
		if(applicationContext == null){
			return null;
		}
		return applicationContext.getEnvironment().getProperty(key);
	}

	/***
	 * 自动解析${key}
	 * @param key
	 * @return
	 */
	public static String resolvePlaceholders(String key) {
		if(applicationContext == null){
			return null;
		}
		return applicationContext.getEnvironment().resolvePlaceholders(key);
	}


	/**
	 * 重写并初始化上下文
	 *
	 * @param applicationContext 应用上下文
	 * @throws BeansException bean异常
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// 初始化applicationContext
		SpringContextHelper.applicationContext = applicationContext;
	}


	/**
	 * 获取response对象
	 * @return the httpServletResponse (type HttpServletRequest) of this SpringContextHelper object.
	 * @author wangLiuJing
	 * Created on 2019/11/12
	 */
	public static Map<String, Object> getHttpServletResponse(Class<? extends Annotation> var1) {
		if(applicationContext == null){
			return null;
		}
		return applicationContext.getBeansWithAnnotation(var1);
	}

	/**
	 * 注册单例对象到spring容器
	 * Created on 2020/1/19
	 * @param name of type String
	 * @param clazz of type Class<T>
	 * @param args of type Object...
	 * @return T
	 */
	public static <T> T registerSingleBean(String name, Class<T> clazz, Object... args) {
		// 容器中是否已经包含此bean
		if(applicationContext.containsBean(name)) {
			Object bean = applicationContext.getBean(name);
			// 判断真实对象是否是参数指定的类型
			if (bean.getClass().isAssignableFrom(clazz)) {
				return (T) bean;
			} else {
				throw new RuntimeException("BeanName 重复 " + name);
			}
		}
		// Spring的bean的定义构造器
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		// 给要注册的bean设置构造函数相应的参数,注意,顺序要相同
		for (Object arg : args) {
			beanDefinitionBuilder.addConstructorArgValue(arg);
		}
		// 获取原始的bean
		BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
		beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
		// 获取bean定义注册的工厂
		BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getParentBeanFactory();
		beanFactory.registerBeanDefinition(name, beanDefinition);
		return applicationContext.getBean(name, clazz);
	}

	/**
	 * 根据路径规则匹配资源集合
	 * @author wangLiuJing
	 * Created on 2021/10/15
	 *
	 * @param locationPattern of type String
	 * @return Resource[]
	 * @throws IOException when
	 */
	@SneakyThrows
	public static Resource[] getResources(String locationPattern) {
		return applicationContext.getResources(locationPattern);
	}

	/**
	 * 根据路径获取资源
	 * @author wangLiuJing
	 * Created on 2021/10/15
	 *
	 * @param location of type String
	 * @return Resource
	 */
	public static Resource getResource(String location){
		return applicationContext.getResource(location);
	}
}

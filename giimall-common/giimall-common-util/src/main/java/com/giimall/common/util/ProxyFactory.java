package com.giimall.common.util;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 代理工厂
 *
 * @author wangLiuJing
 * Created on 2020/12/16
 */
public class ProxyFactory {

	/**
	 * 通过接口来获得代理类
	 * @author wangLiuJing
	 * Created on 2020/12/16
	 *
	 * @param clazz of type Class<T>
	 * @param handler of type InvocationHandler
	 * @return Object
	 */
	public static <T extends E, E> E getImplimentsProxy(Class<T> clazz, InvocationHandler handler, Class<E> returnType) {
		//获得类加载器
		ClassLoader loader = ProxyFactory.class.getClassLoader();
		//获得clazz对象对应实体对象的所有接口
		Class<?>[] interfaces = clazz.getInterfaces();
		//获得代理类
		return (E) Proxy.newProxyInstance(loader, interfaces, handler);
	}

	/**
	 * 通过继承来获得代理类
	 * @author wangLiuJing
	 * Created on 2020/12/16
	 *
	 * @param clazz of type Class<T>
	 * @param interceptor of type MethodInterceptorT
	 * @return Object
	 */
	public static <T> T getExtendsProxy(Class<T> clazz, MethodInterceptor interceptor) {
		// 创建 Cglib 的核心类:
		Enhancer enhancer = new Enhancer();
		//设置代理类的父类
		enhancer.setSuperclass(clazz);
		//设置代理类调用处理程序的调用对象
		enhancer.setCallback(interceptor);
		//获得代理类
		return (T) enhancer.create();
	}

	/**
	 * 通过继承来获得代理类(利用有参构造函数创建代理对象)
	 * @author wangLiuJing
	 * Created on 2020/12/17
	 *
	 * @param clazz of type Class<T>
	 * @param interceptor of type MethodInterceptor
	 * @param argumentTypes of type Class[]
	 * @param arguments of type Object[]
	 * @return T
	 */
	public static <T> T getExtendsProxy(Class<T> clazz, MethodInterceptor interceptor, Class[] argumentTypes,
										Object[] arguments) {
		// 创建 Cglib 的核心类:
		Enhancer enhancer = new Enhancer();
		//设置代理类的父类
		enhancer.setSuperclass(clazz);
		//设置代理类调用处理程序的调用对象
		enhancer.setCallback(interceptor);
		//获得代理类
		return (T) enhancer.create(argumentTypes, arguments);
	}
}

package com.giimall.common.annotation;

import com.giimall.common.processor.MillisecondToSecondImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启雪花算法
 *
 * @author wangLiuJing
 * Created on 2019/11/6
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MillisecondToSecondImportBeanDefinitionRegistrar.class})
public @interface EnableGiimallTime {


}

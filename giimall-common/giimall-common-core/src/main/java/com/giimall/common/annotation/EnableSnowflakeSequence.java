package com.giimall.common.annotation;

import com.giimall.common.config.SnowflakeSequenceAutoConfig;
import com.giimall.common.property.SnowflakeSequenceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@Import({SnowflakeSequenceAutoConfig.class})
@EnableConfigurationProperties(SnowflakeSequenceProperties.class)
public @interface EnableSnowflakeSequence {


}

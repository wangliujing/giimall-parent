package com.giimall.common.annotation;

import com.giimall.common.config.RabbitMqAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 开启RabbitMq 消息Json序列化
 * @author wangLiuJing
 * @date 2022/04/13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RabbitMqAutoConfig.class})
public @interface EnableRabbitMqJacksonMessageConverter {
}

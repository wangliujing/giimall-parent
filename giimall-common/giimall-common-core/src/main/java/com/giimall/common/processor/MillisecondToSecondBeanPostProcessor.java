package com.giimall.common.processor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.SpringBootConfiguration;

import java.io.IOException;
import java.util.Date;

/**
 * 主要解决前端框架时间戳单位是秒的问题，把时间戳转换为秒返回前端
 *
 * @author wangLiuJing
 * Created on 2022/3/9
 */
@SpringBootConfiguration
public class MillisecondToSecondBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

	@Override
	public  boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		if(bean instanceof ObjectMapper){
			ObjectMapper objectMapper = (ObjectMapper) bean;
			SimpleModule simpleModule = new SimpleModule();
			simpleModule.addSerializer(Date.class, new JsonSerializer<Date>() {
				@Override
				public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
					gen.writeNumber(value.getTime() / 1000);
				}
			});
			objectMapper.registerModule(simpleModule);
		}
		return true;
	}
}

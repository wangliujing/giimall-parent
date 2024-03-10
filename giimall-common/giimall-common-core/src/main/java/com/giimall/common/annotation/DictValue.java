package com.giimall.common.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.giimall.common.dict.DictJsonSerializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DictJsonSerializer.class)
public @interface DictValue {
	/**
	 * 分类
	 * @return {@link String}
	 */
	String value();
}

package com.giimall.common.dict;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.giimall.common.annotation.DictValue;
import com.giimall.common.util.spring.SpringContextHelper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Map;

/**
 * 字典值序列化器
 *
 * @author wangLiuJing
 * @date 2022/03/15
 */
@AllArgsConstructor
@NoArgsConstructor
public class DictJsonSerializer extends JsonSerializer<Object> implements ContextualSerializer {

	private IDictProvider dictProvider;

	private String classify;

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		Map<Object, String> dictMap = dictProvider.getDictMap(classify);
		gen.writeObject(dictMap.get(value));
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty)
			throws JsonMappingException {
		if(beanProperty != null){
			DictValue contextAnnotation = beanProperty.getAnnotation(DictValue.class);
			if(contextAnnotation != null){
				if(dictProvider == null){
					// 从容器获取对象，业务端要提供DictProvider的实现类
					dictProvider = SpringContextHelper.getBean(IDictProvider.class);
				}
				return new DictJsonSerializer(dictProvider, contextAnnotation.value());
			}
			return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
		}
		return serializerProvider.findNullValueSerializer(null);
	}
}

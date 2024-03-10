package com.giimall.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.giimall.common.util.StringUtil;
import org.springframework.util.Base64Utils;

import java.io.IOException;

/**
 * Base64解码转字符串
 *
 * @author wangLiuJing
 * Created on 2020/8/6
 */
public class Base64ToStringJsonSerializer extends JsonSerializer<String> {
	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(StringUtil.isNotBlank(value)){
			byte[] bytes = Base64Utils.decodeFromString(value);
			gen.writeString(new String(bytes));
		}
	}
}

package com.giimall.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * 时间转换为Long
 *
 * @author wangLiuJing
 * Created on 2020/9/23
 */
public class DateToLongJsonSerializer extends JsonSerializer<Date> {
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(date != null){
			gen.writeNumber(date.getTime());
		}
	}
}

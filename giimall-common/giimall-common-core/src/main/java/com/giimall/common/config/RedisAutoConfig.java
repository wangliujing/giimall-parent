package com.giimall.common.config;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis自动配置类
 *
 * @author wangLiuJing
 * Created on 2021/9/2
 */
@SpringBootConfiguration
@ConditionalOnClass(RedisOperations.class)
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisAutoConfig {

	@Bean
	@ConditionalOnMissingBean
	public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);

		ProtostuffRedisSerializer serializer = new ProtostuffRedisSerializer();
		template.setValueSerializer(serializer);
		template.setHashValueSerializer(serializer);
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(serializer);
		return template;
	}

	/**
	 * 为了提高对象序列化效率，采用Protostuff虚拟化
	 *
	 * @author wangLiuJing
	 * @date 2022/03/16
	 */
	public class ProtostuffRedisSerializer implements RedisSerializer<Object> {

		private final Schema<ProtoWrapper> schema;

		public ProtostuffRedisSerializer() {
			this.schema = RuntimeSchema.getSchema(ProtoWrapper.class);
		}


		@Override
		public byte[] serialize(Object t) throws SerializationException {
			if (t == null) {
				return new byte[0];
			}
			ProtoWrapper wrapper = new ProtoWrapper();
			wrapper.data = t;
			LinkedBuffer allocate = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
			return ProtostuffIOUtil.toByteArray(wrapper, schema, allocate);
		}

		@Override
		public Object deserialize(byte[] bytes) throws SerializationException {
			if (isEmpty(bytes)) {
				return null;
			}
			ProtoWrapper newMessage = new ProtoWrapper();
			ProtostuffIOUtil.mergeFrom(bytes, newMessage, schema);
			return newMessage.data;
		}
	}

	private boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}

	private static class ProtoWrapper {
		private Object data;
	}
}

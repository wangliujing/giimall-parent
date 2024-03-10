/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.util;

import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * redis工具类
 *
 * @author wangLiuJing
 * @version Id: RedisHelper, v1.0.0 2022年03月28日 9:57 wangLiuJing Exp $ 
 */
public class RedisHelper {

	private RedisTemplate redisTemplate;

	public RedisHelper(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}


	/**
	 * hscan键values
	 *
	 * @param key         键
	 * @param hkeyPattern hash键匹配模式
	 * @param count 批量获取数量
	 * @return {@link List}<{@link Map.Entry}<{@link Object}, {@link Object}>>
	 */
	public List<Map.Entry<Object, Object>> hscanKeyValues(String key, String hkeyPattern, int count) throws IOException {
		Cursor<Map.Entry<Object, Object>> cursor = null;
		try {
			List<Map.Entry<Object, Object>> result = new ArrayList<>();
			cursor = redisTemplate.opsForHash().scan(key, ScanOptions.scanOptions()
					.count(count).match(hkeyPattern).build());
			while (cursor.hasNext()) {
				result.add(cursor.next());
			}
			return result;
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}


	/**
	 * @param key           键
	 * @param hkeyPattern   hash键匹配模式
	 * @param count         批量数量
	 * @param consumer
	 */
	public void hscanConsumer(String key, String hkeyPattern, int count, BiConsumer<RedisTemplate,
			Map.Entry<Object, Object>> consumer) throws IOException {
		Cursor<Map.Entry<Object, Object>> cursor = null;
		try {
			cursor = redisTemplate.opsForHash().scan(key, ScanOptions.scanOptions()
					.count(count).match(hkeyPattern).build());
			while (cursor.hasNext()) {
				consumer.accept(redisTemplate, cursor.next());
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}



	/**
	 * 扫描键
	 *
	 * @param pattern 匹配模式
	 * @param count   批量扫描数量
	 * @return {@link List}<{@link String}>
	 * @throws IOException ioexception
	 */
	public List<String> scanKeys(String pattern, int count) throws IOException {
		Cursor cursor = null;
		try {
			cursor = (Cursor) redisTemplate.executeWithStickyConnection(redisConnection ->
					new ConvertingCursor<>(redisConnection.scan(ScanOptions.scanOptions().count(count).match(pattern)
							.build()), redisTemplate.getKeySerializer()::deserialize));
			List<String> result = new ArrayList<>(count);
			while (cursor.hasNext()){
				result.add(cursor.next().toString());
			}
			return result;
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * 扫描消费
	 *
	 * @param pattern  匹配模式
	 * @param count    批量扫描数量
	 * @param consumer 消费者
	 */
	public void scanConsumer(String pattern, int count, BiConsumer<RedisTemplate, List<String>> consumer)
			throws IOException {
		Cursor cursor = null;
		try {
			cursor = (Cursor) redisTemplate.executeWithStickyConnection(redisConnection ->
					new ConvertingCursor<>(redisConnection.scan(ScanOptions.scanOptions().count(count).match(pattern)
							.build()), redisTemplate.getKeySerializer()::deserialize));
			List<String> result = new ArrayList<>(count);
			while (cursor.hasNext()){
				result.add(cursor.next().toString());
				if(result.size() == count){
					consumer.accept(redisTemplate, result);
					result.clear();
				}
			}
			// 防止result里面有数据没有被消费
			if(CollectionUtil.isNotEmpty(result)){
				consumer.accept(redisTemplate, result);
				result.clear();
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}

}

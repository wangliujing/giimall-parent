/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2021-2022
 */
package com.giimall.common.dict;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典持有缓存
 * @author wangLiuJing
 * @date 2022/03/16
 */
public class DictHolder {

	/** 通过线程缓存，防止频繁获取字典 */
	private static final ThreadLocal<Map<String, Map<Object, String>>> threadLocal = ThreadLocal.withInitial(() ->
			new HashMap<>(16));

	/**
	 * 获取字典
	 * @param classify 字典分类
	 * @return {@link Map}<{@link Object}, {@link String}>
	 */
	public static Map<Object, String> getDictMap(String classify){
		return threadLocal.get().get(classify);
	}

	/**
	 * 设置字典
	 * @param classify 字典分类
	 * @param dictMap  字典Map
	 * @return {@link Map}<{@link Object}, {@link String}>
	 */
	public static Map<Object, String> setDictMap(String classify, Map<Object, String> dictMap){
		return threadLocal.get().put(classify, dictMap);
	}

	/**
	 * 清除
	 */
	public static void clear(){
		threadLocal.remove();
	}
}

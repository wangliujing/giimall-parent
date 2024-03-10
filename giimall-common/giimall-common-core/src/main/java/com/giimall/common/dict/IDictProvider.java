package com.giimall.common.dict;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典提供器
 * @author wangLiuJing
 * @date 2022/03/15
 */
public interface IDictProvider {



	/**
	 * 获取字典
	 *
	 * @param classify 分类
	 * @return {@link Map}<{@link Object}, {@link String}>
	 */
	default Map<Object, String> getDictMap(String classify){
		Map<Object, String> objectStringMap = DictHolder.getDictMap(classify);
		if(objectStringMap == null){
			objectStringMap = doGetDictMap(classify);
			if(objectStringMap == null){
				objectStringMap = new HashMap<>(16);
			}
			DictHolder.setDictMap(classify, objectStringMap);
		}
		return objectStringMap;
	}

	/**
	 * 获取字典
	 *
	 * @param classify 分类
	 * @return {@link Map}<{@link Object}, {@link String}>
	 */
	Map<Object, String> doGetDictMap(String classify);
}

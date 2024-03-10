package com.giimall.common.poi;

import java.util.Map;

/**
 * 字典处理器
 *
 * @author wangLiuJing
 * Created on 2021/5/24
 */
public interface IDictHandler {

	/** HttpInvoker Url  */
	String SERVICE_URL = "/open/dictHandler";

	/**
	 * 获取字典
	 * @author wangLiuJing
	 * Created on 2021/5/24
	 *
	 * @param code of type String
	 * @return Map<String ,   String>
	 */
	Map<String, String> getDictByCode(String code);
}

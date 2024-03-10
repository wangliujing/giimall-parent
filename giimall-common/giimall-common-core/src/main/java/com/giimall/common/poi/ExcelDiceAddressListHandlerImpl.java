package com.giimall.common.poi;/*
package com.giimall.common.poi;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * Excel导出字典处理
 *
 * @author wangLiuJing
 * Created on 2021/5/24
 *//*

@Data
public class ExcelDiceAddressListHandlerImpl implements IExcelDictHandler {

	private static final String DICT_VALUE = "dictValue";

	private static final String DICT_KEY = "dictKey";

	private IDictHandler defaultDictHandler;


	*/
/** 自定义字典映射  *//*

	private Map<String, IDictHandler> customDictHandlerMapping = new HashMap<>();

	public ExcelDiceAddressListHandlerImpl(IDictHandler defaultDictHandler){
		if(defaultDictHandler == null) {
			throw new RuntimeException("默认处理器不能为空");
		}
		this.defaultDictHandler = defaultDictHandler;
	}


	*/
/** 防止频繁查询字典，把字典缓存在内存  *//*

	private Map<String, List<Map>> dictMap = new HashMap<>(10);


	*/
/**
	 * 注册自定义字典处理器
	 * @author wangLiuJing
	 * Created on 2021/5/25
	 *
	 * @param dict of type String
	 * @param dictHandler of type IDictHandler
	 *//*

	public void registCustomDictHandler(String dict, IDictHandler dictHandler){
		customDictHandlerMapping.put(dict, dictHandler);
	}

	@Override
	public List<Map> getList(String dict) {
		List<Map> dictList = dictMap.get(dict);
		if(dictList != null){
			return dictList;
		}
		IDictHandler customDictHandler = customDictHandlerMapping.get(dict);
		// 如果自定义字典处理器不为空，则优先用自定义处理器
		IDictHandler dictHandler = customDictHandler == null ? defaultDictHandler : customDictHandler;
		Map<String, String> dictByCode = dictHandler.getDictByCode(dict);
		if(dictByCode == null || dictByCode.isEmpty()){
			return null;
		}
		List<Map> newDictList = new ArrayList<>();
		dictByCode.forEach((key, value) -> {
			Map<String, String> map = new HashMap<>(2);
			map.put(DICT_KEY, key);
			map.put(DICT_VALUE, value);
			newDictList.add(map);
		});
		dictMap.put(dict, newDictList);
		return newDictList;
	}
	@Override
	public String toName(String dict, Object o, String name, Object value) {
		List<Map> dictList = getList(dict);
		for (Map<String, String> map : dictList) {
			if(map.get(DICT_KEY).equals(value)){
				return map.get(DICT_VALUE);
			}
		}
		return null;
	}

	@Override
	public String toValue(String dict, Object o, String name, Object value) {
		List<Map> dictList = getList(dict);
		for (Map<String, String> map : dictList) {
			if(map.get(DICT_VALUE).equals(value)){
				return map.get(DICT_KEY);
			}
		}
		return null;
	}
}
*/

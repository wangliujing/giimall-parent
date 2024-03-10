package com.giimall.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giimall.common.exception.CommonException;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;


/**
 * Json和对象互转工具类
 *
 * @author wangLiuJing
 * Created on 2019/9/19
 */
public class JsonUtil {

	private static final String OBJECT_STR_BEGIN = "{";
	private static final String OBJECT_STR_END = "}";
	private static final String ARRAY_STR_BEGIN = "[";
	private static final String ARRAY_STR_END = "]";

	/** 定义jackson对象  */
	private static final ObjectMapper MAPPER_NON_NULL = new ObjectMapper();

	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		// 设置值为空的属性不序列化
		MAPPER_NON_NULL.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	/**
	 * 是否是json对象
	 * @author wangLiuJing
	 * Created on 2022/1/7
	 *
	 * @param json of type String
	 * @return boolean
	 */
	public static boolean isObject(String json){
		if(StringUtil.isBlank(json)){
			return false;
		}
		return json.trim().startsWith(OBJECT_STR_BEGIN);
	}

	/**
	 * 是否是json数组对象
	 * @author wangLiuJing
	 * Created on 2022/1/7
	 *
	 * @param json of type String
	 * @return boolean
	 */
	public static boolean isArray(String json){
		if(StringUtil.isBlank(json)){
			return false;
		}
		return json.trim().startsWith(ARRAY_STR_BEGIN);
	}


	/**
	 * 根据json类型自动转换为Map或者List
	 * @author wangLiuJing
	 * Created on 2022/1/7
	 *
	 * @param json of type String
	 * @return Object
	 */
	public static Object jsonToMapOrList(String json){
		if(isObject(json)){
			return jsonToPojo(json, Map.class);
		} else if (isArray(json)){
			return jsonToPojo(json, List.class);
		} else {
			throw new CommonException(String.format("无效JSON格式 %s", json));
		}
	}

	/**
	 * 将对象转换成json字符串
	 * @author wangLiuJing
	 * Created on 2021/12/31
	 *
	 * @param data of type Object
	 * @param removeNullAttr of type boolean
	 * @return String
	 */
	public static String objectToJson(Object data, boolean removeNullAttr){
		ObjectMapper thisMapper = MAPPER;
		if(removeNullAttr){
			thisMapper = MAPPER_NON_NULL;
		}
		try {
			String string = thisMapper.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Json解析错误", e);
		}
	}


	/**
	 * 将对象转换成json字符串。
	 * <p>Title: pojoToJson</p>
	 * <p>Description: </p>
	 *
	 * @param data
	 * @return
	 */
	public static String objectToJson(Object data) {
		return objectToJson(data, true);
	}

	/**
	 * 将json结果集转化为对象
	 *
	 * @param jsonData json数据
	 * @param beanType 对象中的object类型
	 * @return
	 */
	public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
		try {
			T t = MAPPER_NON_NULL.readValue(jsonData, beanType);
			return t;
		} catch (Exception e) {
			throw new RuntimeException("Json解析错误", e);
		}
	}

	/**
	 * 字节输入流转Java对象
	 * @param inputStream
	 * @param beanType
	 * @return {@link T}
	 */
	public static <T> T inputStreamToPojo(InputStream inputStream, Class<T> beanType) {
		try {
			T t = MAPPER_NON_NULL.readValue(inputStream, beanType);
			return t;
		} catch (Exception e) {
			throw new RuntimeException("Json解析错误", e);
		}
	}

	/**
	 * 字符输入流转Java对象
	 * @param reader
	 * @param beanType
	 * @return {@link T}
	 */
	public static <T> T readerToPojo(Reader reader, Class<T> beanType) {
		try {
			T t = MAPPER_NON_NULL.readValue(reader, beanType);
			return t;
		} catch (Exception e) {
			throw new RuntimeException("Json解析错误", e);
		}
	}

	/**
	 * 将json数据转换成pojo对象list
	 * <p>Title: jsonToList</p>
	 * <p>Description: </p>
	 *
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
		JavaType javaType = MAPPER_NON_NULL.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = MAPPER_NON_NULL.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("Json解析错误", e);
		}

	}

	/**
	 * 对象转换成目标对象
	 *
	 * @param data
	 * @param beanType
	 * @return
	 */
	public static <T> T objectToObject(Object data, Class<T> beanType) {
		return jsonToPojo(objectToJson(data), beanType);
	}


	/**
	 * 判断是否是一个标准的json字符串
	 *
	 * @param json
	 * @return
	 */
	public final static boolean isJSON(Object json) {
		if(json == null || !(json instanceof String) || "".equals(json)) {
			return false;
		}
		String jsonStr = (String) json;
		if(jsonStr.startsWith(OBJECT_STR_BEGIN) && jsonStr.endsWith(OBJECT_STR_END)) {
			return true;
		}

		if(jsonStr.startsWith(ARRAY_STR_BEGIN) && jsonStr.endsWith(ARRAY_STR_END)) {
			return true;
		}
		return false;
	}

	/**
	 * 美化json
	 * @param json
	 * @return {@link String}
	 */
	public static String beautify(String json) {
		StringBuilder result = new StringBuilder();
		json = json.replace(" ", "");
		int length = json.length();
		int number = 0;
		char key;
		//遍历输入字符串。
		for(int i = 0; i < length; i++) {
			//1、获取当前字符。
			key = json.charAt(i);

			//2、如果当前字符是前方括号、前花括号做如下处理：
			if((key == '[') || (key == '{')) {
				//（1）打印：当前字符。
				result.append(key);

				//（2）前方括号、前花括号，的后面必须换行。打印：换行。
				result.append('\n');

				//（3）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
				number++;
				result.append(indent(number));

				//（4）进行下一次循环。
				continue;
			}

			//3、如果当前字符是后方括号、后花括号做如下处理：
			if((key == ']') || (key == '}')) {
				//（1）后方括号、后花括号，的前面必须换行。打印：换行。
				result.append('\n');

				//（2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
				number--;
				result.append(indent(number));

				//（3）打印：当前字符。
				result.append(key);

				//（4）继续下一次循环。
				continue;
			}

			//4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
			if((key == ',')) {
				result.append(key);
				result.append('\n');
				result.append(indent(number));
				continue;
			}

			if(StringUtil.isNotBlank(key + "")) {
				result.append(key);
			}

		}
		return result.toString();
	}


	/**
	 * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。
	 *
	 * @param number 缩进次数。
	 * @return 指定缩进次数的字符串。
	 */
	private static String indent(int number) {
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < number; i++) {
			//单位缩进4个字符串。
			result.append("    ");
		}
		return result.toString();
	}

	public static void main(String[] args) {
		String str = "{\"saleOrderNo\":\"XS20220419MMMW\",\"context\":{\"x-request-context\":{\"acceptCurrency\":\"\",\"acceptLanguage\":\"zh-CN\",\"clientIp\":\"123.233.103.121\",\"isAdmin\":true,\"loginId\":\"143\",\"requestClient\":\"seller\",\"shopId\":\"187045493625241616\",\"shopSettings\":\"{\\\"status\\\":true,\\\"defaultLanguageMode\\\":2,\\\"mainCurrency\\\":{\\\"currencyId\\\":163,\\\"code\\\":\\\"USD\\\",\\\"symbol\\\":\\\"$\\\",\\\"decimalLength\\\":1},\\\"mainLanguage\\\":\\\"zh-CN\\\",\\\"language\\\":[\\\"zh-CN\\\",\\\"ja\\\",\\\"zh-TW\\\",\\\"lo\\\",\\\"id\\\",\\\"ko\\\",\\\"en\\\",\\\"ms\\\",\\\"th\\\",\\\"ru-RU\\\"],\\\"morelanguage\\\":[\\\"zh-CN\\\",\\\"en\\\",\\\"ko\\\",\\\"ja\\\",\\\"th\\\",\\\"ms\\\",\\\"ru-RU\\\",\\\"zh-TW\\\",\\\"lo\\\",\\\"id\\\"],\\\"useMultipleCurrency\\\":1,\\\"useFixedExchangeRate\\\":1,\\\"currencyDisplayWay\\\":2,\\\"defaultDisplayWay\\\":2,\\\"currencyList\\\":[{\\\"code\\\":\\\"HKD\\\",\\\"symbol\\\":\\\"HKD\\\",\\\"decimalLength\\\":2,\\\"rate\\\":0.6},{\\\"code\\\":\\\"JPY\\\",\\\"symbol\\\":\\\"¥\\\",\\\"decimalLength\\\":0,\\\"rate\\\":444},{\\\"code\\\":\\\"KRW\\\",\\\"symbol\\\":\\\"₩\\\",\\\"decimalLength\\\":0,\\\"rate\\\":0.4},{\\\"code\\\":\\\"MYR\\\",\\\"symbol\\\":\\\"RM\\\",\\\"decimalLength\\\":2,\\\"rate\\\":0.36}],\\\"timezone\\\":\\\"Asia\\\\/Seoul\\\",\\\"plugins\\\":[1003,1007,1002,1004,1006,1067,1001,1011,1071,1005,1072]}\",\"shopTimezone\":\"Asia/Seoul\",\"userAgent\":\"pc\",\"userInfo\":{\"email\":\"\",\"status\":\"1\",\"userId\":\"143\",\"userName\":\"luwenjie\"}},\"x-request-id\":\"57fc20af6bca2ae9\"}}";
		System.out.println(beautify(str));
	}
}

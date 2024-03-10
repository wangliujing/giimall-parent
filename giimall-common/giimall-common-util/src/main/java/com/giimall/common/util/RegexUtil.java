package com.giimall.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author wangLiuJing
 * Created on 2020/2/27
 */
public class RegexUtil {

	/**
	 * 自定义标记匹配
	 * @author wangLiuJing
	 * Created on 2020/2/27
	 *
	 * @param regex of type String
	 * @param targetString of type String
	 * @param flag of type int
	 *  flags的值如下
	 * 	Pattern.CANON_EQ
	 *           启用规范等价。
	 * 	Pattern.CASE_INSENSITIVE
	 *           启用不区分大小写的匹配。
	 * 	Pattern.COMMENTS
	 *           模式中允许空白和注释。
	 * 	Pattern.DOTALL
	 *           启用 dotall 模式。
	 * 	Pattern.LITERAL
	 *           启用模式的字面值分析。
	 * 	Pattern.MULTILINE
	 *           启用多行模式。
	 * 	Pattern.UNICODE_CASE
	 *           启用 Unicode 感知的大小写折叠。
	 * 	Pattern.UNIX_LINES
	 *           启用 Unix 行模式。
	 *
	 * @return boolean
	 */
	public static boolean flagMatch(String regex, String targetString, int flag){
		return Pattern.compile(regex, flag).matcher(targetString).matches();
	}

	/**
	 * 正常匹配
	 * @author wangLiuJing
	 * Created on 2020/2/27
	 *
	 * @param regex of type String
	 * @param targetString of type String
	 * @return boolean
	 */
	public static boolean match(String regex, String targetString){
		return Pattern.compile(regex).matcher(targetString).matches();
	}

	/**
	 * 获取组字符串
	 * @author wangLiuJing
	 * Created on 2020/2/27
	 *
	 * @param regex of type String
	 * @param targetString of type String
	 * @param groupIndex of type int
	 * @return String
	 */
	public static String getGroupString(String regex, String targetString, int groupIndex){
		Matcher p = Pattern.compile(regex).matcher(targetString);
		if(p.find()){
			return p.group(groupIndex);
		}
		return null;
	}
}

package com.giimall.common.constant;

/**
 * 日期格式化常量
 *
 * @author wangLiuJing
 * Created on 2020/1/16
 */
public class DateFormatConstant {

	/**
	 * 正则表达式
	 *
	 * */
	public static final String NORMAL_REX = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}";

	public static final String ISO_NORMAL_REX = "\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}Z";

	public static final String DATE_REX = "\\d{4}-\\d{1,2}-\\d{1,2}";

	public static final String TIME_REX = "\\d{1,2}:\\d{1,2}:\\d{1,2}";

	public static final String TIME_WITH_MILLISECONDS_REX = "\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}";

	public static final String NORMAL_WITH_MILLISECONDS_REX = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}" +
			".\\d{1,3}";

	public static final String ISO_NORMAL_WITH_MILLISECONDS_REX = "\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}" +
			".\\d{1,3}Z";



	public static final String YEAR = "yyyy";

	public static final String MONTH = "MM";

	public static final String DAY = "dd";

	public static final String HOUR = "HH";

	public static final String MINUTE = "mm";

	public static final String SECOND = "ss";

	public static final String MILLISECOND = "SSS";

	public static final String NORMAL = "yyyy-MM-dd HH:mm:ss";

	public static final String NORMAL_HOUR = "yyyy-MM-dd HH";

	public static final String ISO_NORMAL = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	public static final String DATE = "yyyy-MM-dd";

	public static final String YEAR_MONTH = "yyyy-MM";

	public static final String TIME = "HH:mm:ss";

	public static final String TIME_WITH_MILLISECONDS = "HH:mm:ss.SSS";

	public static final String NORMAL_WITH_MILLISECONDS = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String ISO_NORMAL_WITH_MILLISECONDS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public static final String NORMAL_NO_CONNECTOR = "yyyyMMddHHmmss";

	public static final String DATE_NO_CONNECTOR = "yyyyMMdd";

	public static final String TIME_NO_CONNECTOR = "HHmmss";

	public static final String TIME_WITH_MILLISECONDS_NO_CONNECTOR = "HHmmssSSS";

	public static final String NORMAL_WITH_MILLISECONDS_NO_CONNECTOR = "yyyyMMddHHmmssSSS";

}

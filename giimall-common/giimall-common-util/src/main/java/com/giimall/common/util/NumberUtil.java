package com.giimall.common.util;

import com.giimall.common.exception.CommonException;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 数字相关工具类
 *
 * @author wangLiuJing
 * Created on 2019/11/14
 */
public class NumberUtil {

	private static Pattern pattern = null;

	private static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();

	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

	private static final Random RANDOM = new Random();

	static {
		// 去除千分位
		NUMBER_FORMAT.setGroupingUsed(false);
	}

	public static String numberFormatToPercent(double number, int maximumFractionDigits, int minimumFractionDigits) {
		// 设置最大小数位
		PERCENT_FORMAT.setMaximumFractionDigits(maximumFractionDigits);
		// 设置最小小数位
		PERCENT_FORMAT.setMinimumFractionDigits(minimumFractionDigits);
		return PERCENT_FORMAT.format(number);
	}

	/**
	 * @param number
	 * @param digits 保留小数位数
	 * @return
	 */
	public static double numberFormatDecimalDigits(double number, int digits, RoundingMode roundingMode) {
		NUMBER_FORMAT.setMaximumFractionDigits(digits);
		NUMBER_FORMAT.setRoundingMode(roundingMode);
		return Double.parseDouble(NUMBER_FORMAT.format(number));
	}

	/**
	 * @param number
	 * @param digits 保留小数位数
	 * @return
	 */
	public static double numberFormatDecimalDigits(double number, int digits) {

		return Double.parseDouble(String.format("%." + digits + "f", number));
	}

	/**
	 * @param number
	 * @param pattern 例如"00"
	 * @return
	 */
	public static String intFormatToString(Object number, String pattern) {
		Format format = new DecimalFormat(pattern);
		return format.format(number);
	}

	/**
	 * Method getPages ...
	 *
	 * @param totals   of type long  总页数
	 * @param pageSize of type int 每页大小
	 * @return long
	 * @author wangLiuJing
	 * Created on 2019/11/15
	 */
	public static long getPages(long totals, int pageSize) {
		return (totals + pageSize - 1) / pageSize;
	}

	/**
	 * 获取Map初始化长度
	 *
	 * @param length
	 * @param loadFactor 负载因子传空、则使用0.75
	 * @return int 待存储数据长度
	 */
	public static int getMapInitializeSize(int length, Double loadFactor) {
		if(loadFactor == null) {
			loadFactor = 0.75;
		}
		return ((Double) (length / loadFactor)).intValue() + 1 ;
	}

	/**
	 * 随机数
	 * @author wangLiuJing
	 * Created on 2020/2/13
	 *
	 * @param digit of type int  随机数的位数 digit不能小于1
	 * @return long
	 */
	public static long randomNumber(int digit) {
		if(digit < 1){
			throw new CommonException("digit参数值不能小于1");
		}
		return (long) ((Math.random() * 9 + 1) * Math.pow(10, digit - 1));
	}

	/**
	 * 生成0到number的随机数不包含number
	 * @author wangLiuJing
	 * Created on 2020/6/10
	 *
	 * @param number of type long
	 * @return int
	 */
	public static int randomNextInt(int number){
		return RANDOM.nextInt(number);
	}


	/**
	 * 判断字符串是否是数字
	 * @author wangLiuJing
	 * Created on 2020/9/17
	 *
	 * @param str of type String
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		if(pattern == null){
			pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
		}
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

}


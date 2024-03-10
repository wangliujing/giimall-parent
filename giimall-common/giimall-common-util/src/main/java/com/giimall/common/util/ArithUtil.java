package com.giimall.common.util;


import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
 * 确的浮点数运算，包括加减乘除和四舍五入。
 *
 * @author wangLiuJing
 * Created on 2022/3/3
 */
public class ArithUtil {

	/**
	 * 默认除法运算精度
	 */
	private static final int DEF_DIV_SCALE = 10;


	/**
	 * 精确的加法算法
	 *
	 * @param d1 of type double
	 * @param d2 of type double
	 * @return double
	 * @author wangLiuJing
	 * Created on 2022/3/3
	 */
	public static double add(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.add(b2).doubleValue();

	}

	/**
	 * 精确的减法算法
	 * @author wangLiuJing
	 * Created on 2022/3/3
	 *
	 * @param d1 of type double
	 * @param d2 of type double
	 * @return double
	 */
	public static double subtract(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.subtract(b2).doubleValue();

	}


	/**
	 * 精确的乘法算法
	 * @author wangLiuJing
	 * Created on 2022/3/3
	 *
	 * @param d1 of type double
	 * @param d2 of type double
	 * @return double
	 */
	public static double multiply(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.multiply(b2).doubleValue();

	}


	/**
	 * 相对精确的除法运算，当发生除不尽的情况时，精确到小数点以后10位
	 * @author wangLiuJing
	 * Created on 2022/3/3
	 *
	 * @param d1 of type double
	 * @param d2 of type double
	 * @return double
	 */
	public static double divide(double d1, double d2) {
		return divide(d1, d2, DEF_DIV_SCALE);
	}


	/**
	 * 相对精确的除法运算，当发生除不尽的情况时，精确到小数点以后指定精度(scale)，再往后的数字四舍五入
	 * @author wangLiuJing
	 * Created on 2022/3/3
	 *
	 * @param d1 of type double
	 * @param d2 of type double
	 * @param scale of type int
	 * @return double
	 */
	public static double divide(double d1, double d2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();

	}

	/**
	 * double 取余
	 *
	 * @param d1 d1
	 * @param d2 d2
	 * @return double
	 */
	public static double remainder(double d1, double d2){
		BigDecimal bd1 = BigDecimal.valueOf(d1);
		BigDecimal bd2 = BigDecimal.valueOf(d2);
		return bd1.divideAndRemainder(bd2)[1].doubleValue();
	}

} 
/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2018
 */
package com.giimall.common.util;


import com.giimall.common.exception.CommonException;

import java.util.Collection;

/**
 * 参数/数据 - 断言工具类
 *
 * @author 白清(侯俊昌)
 * @version $Id: AssertsUtil, v1.0.0 2016年3月7日 下午8:32:46 白清(侯俊昌) Exp $
 */
public class AssertUtil {

	/**
	 * 校验字符串为空
	 *
	 * @param value   校验字符串
	 * @param message 错误信息
	 * @throws CommonException 异常对象
	 */
	public static void isBlank(String value, String message) throws CommonException {

		// 校验objValue是否为空，为空则抛出异常
		if (StringUtil.isNotBlank(value)) {
			throw new CommonException(message);
		}
	}


    /**
     * 校验字符串为空
     *
     * @param value  值
     * @param format 格式化
     * @param args   参数
     * @throws CommonException 常见异常
     */
    public static void isBlank(String value, String format, Object... args) throws CommonException {

        // 校验objValue是否为空，为空则抛出异常
        if (StringUtil.isNotBlank(value)) {
            throw new CommonException(String.format(format, args));
        }
    }

	/**
	 * 校验字符串不为空
	 *
	 * @param value   校验字符串
	 * @param message 错误信息
	 * @throws CommonException 异常对象
	 */
	public static void isNotBlank(String value, String message) throws CommonException {

		// 校验objValue是否为空，为空则抛出异常
		if (StringUtil.isBlank(value)) {
			throw new CommonException(message);
		}
	}


	/**
	 * 校验字符串不为空
	 *
	 * @param value  值
	 * @param format 格式化
	 * @param args   参数
	 * @throws CommonException 异常对象
	 */
	public static void isNotBlank(String value, String format, Object... args) throws CommonException {

		// 校验objValue是否为空，为空则抛出异常
		if (StringUtil.isBlank(value)) {
			throw new CommonException(String.format(format, args));
		}
	}



	/**
	 * 校验对象为NULL
	 *
	 * @param obj     校验对象
	 * @param message 错误信息
	 * @throws CommonException 异常对象
	 */
	public static void isNull(Object obj, String message) throws CommonException {

		// 校验Object是否为null，不为null则抛出异常
		if (null != obj) {
			throw new CommonException(message);
		}
	}


    /**
     * 校验对象为NULL
     * @param obj
     * @param format
     * @param args
     * @throws CommonException
     */
    public static void isNull(Object obj, String format, Object... args) throws CommonException {

        // 校验Object是否为null，不为null则抛出异常
        if (null != obj) {
            throw new CommonException(String.format(format, args));
        }
    }

	/**
	 * 校验对象不为NULL
	 *
	 * @param obj     校验对象
	 * @param message 错误信息
	 * @throws CommonException 异常对象
	 */
	public static void isNotNull(Object obj, String message) throws CommonException {

		// 校验Object是否为null，为null则抛出异常
		if (null == obj) {
			throw new CommonException(message);
		}
	}

    /**
     * 校验对象不为NULL
     * @param obj
     * @param format
     * @param args
     * @throws CommonException
     */
    public static void isNotNull(Object obj, String format, Object... args) throws CommonException {

        // 校验Object是否为null，为null则抛出异常
        if (null == obj) {
            throw new CommonException(String.format(format, args));
        }
    }

	/**
	 * 校验参数为TRUE
	 *
	 * @param value   校验布尔值
	 * @param message 错误信息
	 * @throws CommonException 异常对象
	 */
	public static void isTrue(boolean value, String message) throws CommonException {

		// 校验value是否为true，为true则抛出异常
		if (!value) {
			throw new CommonException(message);
		}
	}

	/**
	 * 校验参数为FALSE
	 *
	 * @param value   校验布尔值
	 * @param message 错误信息
	 * @throws CommonException 异常对象
	 */
	public static void isFalse(boolean value, String message) throws CommonException {

		// 校验value是否为false，为false则抛出异常
		if (value) {
			throw new CommonException(message);
		}
	}

	/**
	 * 校验参数为0
	 *
	 * @param value   校验参数
	 * @param message 错误信息
	 * @throws CommonException 异常对象
	 */
	public static void isZero(Number value, String message) throws CommonException {

		// 校验value是否为0，不为0则抛出异常
		if (value == null || value.doubleValue() != 0) {
			throw new CommonException(message);
		}
	}

	/**
	 * 校验参数不为0
	 *
	 * @param value   校验参数
	 * @param message 错误信息
	 * @throws CommonException 异常对象
	 */
	public static void isNotZero(Number value, String message) throws CommonException {

		// 校验value是否为0，为0则抛出异常
		if (value == null || value.doubleValue() == 0) {
			throw new CommonException(message);
		}
	}

    /**
     * 校验数字是否是null或0
     *
     * @param value   校验参数
     * @param message 错误信息
     * @throws CommonException 异常对象
     */
    public static void isInitialize(Number value, String message) throws CommonException {

        // 校验value是否为0，不为0则抛出异常
        if (value != null || value.doubleValue() == 0) {
            throw new CommonException(message);
        }
    }


    /**
     * 校验数字是否是null或0
     * @param value  值
     * @param format
     * @param args
     * @throws CommonException
     */
    public static void isInitialize(Number value, String format, Object... args) throws CommonException {

        // 校验value是否为0，不为0则抛出异常
        if (value != null || value.doubleValue() != 0) {
            throw new CommonException(String.format(format, args));
        }
    }

    /**
     * 校验数字不是null或0
     *
     * @param value   校验参数
     * @param message 错误信息
     * @throws CommonException 异常对象
     */
    public static void isNotInitialize(Number value, String message) throws CommonException {

        // 校验value是否为0，为0则抛出异常
        if (value == null || value.doubleValue() == 0) {
            throw new CommonException(message);
        }
    }


    /**
     * 校验数字不是null或0
     * @param value  值
     * @param format
     * @param args
     * @throws CommonException
     */
    public static void isNotInitialize(Number value, String format, Object... args) throws CommonException {

        // 校验value是否为0，为0则抛出异常
        if (value == null || value.doubleValue() == 0) {
            throw new CommonException(String.format(format, args));
        }
    }


	/**
	 * 校验参数为负数
	 *
	 * @param value   校验参数
	 * @param message 错误信息
	 * @throws CommonException 异常对象
	 */
	public static void isNotNegative(Number value, String message) throws CommonException {

		// 校验value是否为0，为0则抛出异常
		if (value != null && value.doubleValue() < 0) {
			throw new CommonException(message);
		}
	}

	/**
	 * 校验集合是否为空
	 *
	 * @param value   校验集合
	 * @param message 错误信息
	 * @throws CommonException 异常对象
	 */
	public static void isEmpty(Collection value, String message) throws CommonException {
		if (CollectionUtil.isNotEmpty(value)) {
			throw new CommonException(message);
		}
	}
	/**
	 * 校验集合不为NULL
	 *
	 * @param collection    校验集合
	 * @param message 错误信息
	 * @throws CommonException
	 */
	public static void isNotEmpty(Collection collection, String message) throws CommonException {
		// 校验List是否为null，为null则抛出异常
		if (CollectionUtil.isEmpty(collection)) {
			throw new CommonException(message);
		}
	}

	/**
	 * 校验字符串包含字符
	 *
	 * @param value      校验参数
	 * @param searchChar 搜索字符
	 * @param message    错误信息
	 */
	public static void isContains(String value, String searchChar, String message) {

		// 为空校验
		if (StringUtil.isBlank(value) || StringUtil.isBlank(searchChar)) {
			throw new CommonException(message);
		}

		// 不包含校验
		if (!StringUtil.contains(value, searchChar)) {
			throw new CommonException(message);
		}

	}

	/**
	 * 校验字符串不包含字符
	 *
	 * @param value      校验参数
	 * @param searchChar 搜索字符
	 * @param message    错误信息
	 */
	public static void isNotContains(String value, String searchChar, String message) {

		// 为空校验
		if (StringUtil.isBlank(value) || StringUtil.isBlank(searchChar)) {
			throw new CommonException(message);
		}

		// 不包含校验
		if (StringUtil.contains(value, searchChar)) {
			throw new CommonException(message);
		}

	}

	/**
	 * 校验字符串相同
	 *
	 * @param source  来源值
	 * @param target  目标值
	 * @param message 错误信息
	 */
	public static void isEquals(String source, String target, String message) {

		// 不包含校验
		if (!StringUtil.equals(source, target)) {
			throw new CommonException(message);
		}

	}

	/**
	 * 校验字符串相同
	 *
	 * @param source  来源值
	 * @param target  目标值
	 * @param format 错误信息
	 * @param args 错误信息参数
	 */
	public static void isEquals(String source, String target, String format,  Object... args) {

		// 不包含校验
		if (!StringUtil.equals(source, target)) {
			throw new CommonException(String.format(format, args));
		}

	}

	/**
	 * 校验字符串不相同
	 *
	 * @param source  来源值
	 * @param target  目标值
	 * @param message 错误信息
	 */
	public static void isNotEquals(String source, String target, String message) {
		// 包含校验
		if (StringUtil.equals(source, target)) {
			throw new CommonException(message);
		}
	}

}
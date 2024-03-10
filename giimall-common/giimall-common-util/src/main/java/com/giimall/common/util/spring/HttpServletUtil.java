/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.util.spring;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: HttpServletUtil, v1.0.0 2022年10月15日 13:49 wangLiuJing Exp $ 
 */
public class HttpServletUtil {

	/**
	 * 获取request对象
	 *
	 * @return the httpServletRequest (type HttpServletRequest) of this SpringContextHelper object.
	 * @author wangLiuJing
	 * Created on 2019/11/12
	 */
	public static HttpServletRequest getHttpServletRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null) {
			return null;
		}
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}

	/**
	 * 获取response对象
	 *
	 * @return the httpServletResponse (type HttpServletRequest) of this SpringContextHelper object.
	 * @author wangLiuJing
	 * Created on 2019/11/12
	 */
	public static HttpServletResponse getHttpServletResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
}

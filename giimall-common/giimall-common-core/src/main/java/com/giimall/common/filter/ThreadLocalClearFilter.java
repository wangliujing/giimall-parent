/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2021-2022
 */
package com.giimall.common.filter;

import com.giimall.common.dict.DictHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * ThreadLocal 请求清除过滤器
 * @author wangLiuJing
 * @date 2022/03/16
 */
public class ThreadLocalClearFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		filterChain.doFilter(servletRequest, servletResponse);
		// 清除防止内存泄漏
		DictHolder.clear();
	}
}

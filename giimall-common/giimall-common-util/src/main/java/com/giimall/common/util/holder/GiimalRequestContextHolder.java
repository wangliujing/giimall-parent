package com.giimall.common.util.holder;

import com.giimall.common.constant.SystemConstant;
import com.giimall.common.model.dto.request.*;
import com.giimall.common.util.JsonUtil;
import com.giimall.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.TimeZone;

/**
 * 请求上下文持有器
 *
 * @author wangLiuJing
 * Created on 2022/3/2
 */
@Slf4j
public class GiimalRequestContextHolder {


    private static final ThreadLocal<RequestBodyContext> threadLocal = new ThreadLocal<>();

    /**
     * 设置请求上下文
     *
     * @param requestBodyContext 请求体上下文中
     */
    public static void setRequestContext(RequestBodyContext requestBodyContext) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes == null) {
            threadLocal.set(requestBodyContext);
        } else {
            setRequestContext(requestAttributes.getRequest(), requestBodyContext);
        }

    }

    /**
     * 设置请求上下文
     */
    public static void clearRequestContext() {
        threadLocal.remove();
    }

    /**
     * 设置请求上下文
     *
     * @param request            请求
     * @param requestBodyContext 请求体上下文中
     */
    public static void setRequestContext(HttpServletRequest request, RequestBodyContext requestBodyContext) {
        request.setAttribute(SystemConstant.REQUEST_CONTEXT, requestBodyContext);
    }

    /**
     * 设置请求上下文
     *
     * @param request            请求
     * @param requestBodyContext 请求体上下文中
     */
    /*public static void setRequestContext(HttpServletRequest request, String requestBodyContext) {
        request.setAttribute(SystemConstant.REQUEST_CONTEXT_STRING, requestBodyContext);
    }*/


    /**
     * 获取请求体上下文
     *
     * @return {@link String}
     */
/*	public static String getStringRequestBodyContext(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if(requestAttributes != null) {
			HttpServletRequest request = requestAttributes.getRequest();
			if(request != null) {
				return (String) request.getAttribute(SystemConstant.REQUEST_CONTEXT_STRING);
			}
		}
		return null;
	}*/


    /**
     * 获取请求体上下文
     *
     * @return {@link RequestBodyContext}
     */
    public static RequestBodyContext getRequestBodyContext() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes == null) {
            return threadLocal.get();
        }
        RequestBodyContext attribute = (RequestBodyContext) requestAttributes.getRequest().getAttribute(SystemConstant.REQUEST_CONTEXT);
        //String attributeStr = (String) requestAttributes.getRequest().getAttribute(SystemConstant.REQUEST_CONTEXT_STRING);
        log.debug("获取上下文，attribute ==>> ：{}", attribute);
        return attribute;
    }

    /**
     * 获取请求上下文
     *
     * @return {@link RequestContext}
     */
    public static RequestContext getRequestContext() {
        RequestBodyContext requestBodyContext = getRequestBodyContext();
        if (requestBodyContext != null) {
            return requestBodyContext.getContext();
        }
        return null;
    }

    /**
     * 获取xrequest上下文
     *
     * @return {@link XReqeustContext}
     */
    public static XReqeustContext getXRequestContext() {
        RequestContext requestContext = getRequestContext();
        if (requestContext != null) {
            return requestContext.getXRequestContext();
        }
        return null;
    }

    /**
     * 获取请求id
     *
     * @return {@link String}
     */
    public static String getRequestId() {
        RequestContext requestContext = getRequestContext();
        if (requestContext != null) {
            return requestContext.getXRequestId();
        }
        return null;
    }

    /**
     * 获取商店设置
     *
     * @return {@link ShopSettings}
     */
    public static ShopSettings getShopSettings() {
        XReqeustContext xRequestContext = getXRequestContext();
        if (xRequestContext != null && StringUtil.isNotBlank(xRequestContext.getShopSettings())) {
            return JsonUtil.jsonToPojo(xRequestContext.getShopSettings(), ShopSettings.class);
        }
        return null;
    }

    /**
     * 得到时区
     *
     * @return {@link TimeZone}
     */
    public static String getStringTimeZone() {
        XReqeustContext xRequestContext = getXRequestContext();
        if (xRequestContext != null) {
            return xRequestContext.getShopTimezone();
        }
        return null;
    }

    /**
     * 获取时间区
     *
     * @return {@link TimeZone}
     */
    public static TimeZone getTimeZone() {
        XReqeustContext xRequestContext = getXRequestContext();
        if (xRequestContext != null && StringUtil.isNotBlank(xRequestContext.getShopTimezone())) {
            return TimeZone.getTimeZone(xRequestContext.getShopTimezone());
        }
        return null;
    }

    /**
     * 获取登录用户
     *
     * @return {@link UserInfo}
     */
    public static UserInfo getLoginUser() {
        XReqeustContext xRequestContext = getXRequestContext();
        if (xRequestContext != null) {
            return xRequestContext.getUserInfo();
        }
        return null;
    }

}

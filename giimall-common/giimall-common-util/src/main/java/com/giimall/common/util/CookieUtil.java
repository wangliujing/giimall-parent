package com.giimall.common.util;

import lombok.SneakyThrows;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * Cookie 工具类
 *
 * @author wangLiuJing
 * Created on 2019/12/8
 */
public final class CookieUtil {

    /**
     * 得到Cookie的值, 不解码
     * 
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }
    
    
    /**
     * 得到Cookie的值, utf-8解码
     * 
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieDecodeValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, true);
    }

    /**
     * 指定解码字符集，得到Cookie的值,
     * 
     * @param request
     * @param cookieName
     * @return
     */
    @SneakyThrows
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        for (int i = 0; i < cookieList.length; i++) {
            if (cookieList[i].getName().equals(cookieName)) {
                if (isDecoder) {
                    retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                } else {
                    retValue = cookieList[i].getValue();
                }
                break;
            }
        }
        return retValue;
    }

    /**
     * 得到Cookie的值,
     * 
     * @param request
     * @param cookieName
     * @return
     */
    @SneakyThrows
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        for (int i = 0; i < cookieList.length; i++) {
            if (cookieList[i].getName().equals(cookieName)) {
                retValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
                break;
            }
        }
        return retValue;
    }

    /**
     * 设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, -1);
    }

    /**
     * 设置Cookie的值 在指定时间内生效,但不编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue, int cookieMaxage) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
    }

    /**
     * 设置Cookie的值 不设置生效时间,但编码
     */
    public static void setEncodingCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, -1, true);
    }

    /**
     * 设置Cookie的值 在指定时间内生效, 指定参数是否编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue, int cookieMaxage, boolean isEncode) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
    }

    /**
     * 设置Cookie的值 在指定时间内生效, 编码参数(指定编码)
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue, int cookieMaxage, String encodeString) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
    }

    /**
     * 删除Cookie带cookie域名
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
            String cookieName) {
        doSetCookie(request, response, cookieName, "", -1, false);
    }

    /**
     * 设置Cookie的值，并使其在指定时间内生效，默认utf-8编码
     * 
     * @param cookieMaxage cookie生效的最大秒数
     */
    @SneakyThrows
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response,
        String cookieName, String cookieValue, int cookieMaxage, boolean isEncode) {
        if (cookieValue == null) {
            cookieValue = "";
        } else if (isEncode) {
            cookieValue = URLEncoder.encode(cookieValue, "utf-8");
        }
        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (cookieMaxage > 0){
            cookie.setMaxAge(cookieMaxage);
        }

        if (null != request) {// 设置域名的cookie
            String domainName = getDomainName(request);
            if (!"localhost".equals(domainName)) {
                cookie.setDomain(domainName);
            }
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 设置Cookie的值，并使其在指定时间内生效，指定编码集encodeString
     * 
     * @param cookieMaxage cookie生效的最大秒数
     */
    @SneakyThrows
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response,
        String cookieName, String cookieValue, int cookieMaxage, String encodeString) {
        if (cookieValue == null) {
            cookieValue = "";
        } else {
            cookieValue = URLEncoder.encode(cookieValue, encodeString);
        }
        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (cookieMaxage > 0){
            cookie.setMaxAge(cookieMaxage);
        }
        // 设置域名的cookie
        if (null != request) {
            String domainName = getDomainName(request);
            if (!"localhost".equals(domainName)) {
                cookie.setDomain(domainName);
            }
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 得到cookie的域名
     */
    private static final String getDomainName(HttpServletRequest request) {
        String domainName = null;

        String serverName = request.getRequestURL().toString();
        if (serverName == null || serverName.equals("")) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }

        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }

}

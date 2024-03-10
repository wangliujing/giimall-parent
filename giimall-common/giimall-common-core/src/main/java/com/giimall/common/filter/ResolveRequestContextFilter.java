package com.giimall.common.filter;

import com.giimall.common.constant.CharsetConstant;
import com.giimall.common.model.dto.request.RequestBodyContext;
import com.giimall.common.model.dto.request.RequestContext;
import com.giimall.common.util.HttpUtil;
import com.giimall.common.util.JsonUtil;
import com.giimall.common.util.StringUtil;
import com.giimall.common.util.holder.GiimalRequestContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

/**
 * 请求过滤器，主要目的是解析登录用户信息，并封装的request域
 * 信息由网关透传过来
 * {"x-request-context": {
 * "acceptCurrency": "",
 * "acceptLanguage": "zh_CN",
 * "clientIp": "127.0.0.1",
 * "isAdmin": true,
 * "loginId": "13",
 * "requestClient": "seller",
 * "shopId": "145783563507302424",
 * "shopSettings": "{\"status\":true,\"defaultLanguageMode\":2,\"mainCurrency\":{\"currencyId\":162,\"code\":\"CNY\",\"symbol\":\"￥\",\"decimalLength\":2},\"mainLanguage\":\"zh-CN\",\"language\":[\"zh-CN\"],\"morelanguage\":[\"zh-CN\",\"en\",\"KO\"],\"useMultipleCurrency\":1,\"useFixedExchangeRate\":1,\"currencyDisplayWay\":1,\"defaultDisplayWay\":2,\"currencyList\":[],\"timezone\":\"America\\/Scoresbysund\",\"plugins\":[1002]}",
 * "shopTimezone": "America/Scoresbysund",
 * "userAgent": "pc",
 * "userInfo": {
 * "email": "gaojiande126@126.com",
 * "status": "1",
 * "userId": "13",
 * "userName": "么么哒么么哒"
 * }* 	},
 * "x-request-id": "d9aeb84f90bd6bef"
 * }
 *
 * @author wangLiuJing
 * Created on 2021/12/2
 */
@Slf4j
public class ResolveRequestContextFilter implements Filter {

    public static final String X_REQUEST_CONTEXT = "X-Request-Context";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        String requestContextStr = httpServletRequest.getHeader(X_REQUEST_CONTEXT);
        if(StringUtil.isNotBlank(requestContextStr)) {
            RequestBodyContext requestBodyContext = new RequestBodyContext();
            requestContextStr = URLDecoder.decode(requestContextStr, CharsetConstant.CHARSET_UTF_8);
            log.debug("请求头上下文：{}", requestContextStr);
            RequestContext requestContext = JsonUtil.jsonToPojo(requestContextStr, RequestContext.class);
            requestBodyContext.setContext(requestContext);
            GiimalRequestContextHolder.setRequestContext(httpServletRequest, requestBodyContext);
        }
        chain.doFilter(request, response);
    }


   /* private HttpServletRequest getNewRequestByNewGateWay(HttpServletRequest httpServletRequest) throws IOException {
        String requestBody = HttpUtil.getRequestBody(httpServletRequest);
        String requestContextStr = httpServletRequest.getHeader(X_REQUEST_CONTEXT);




        ReconstructionBodyServletRequest newHttpServletRequest = new ReconstructionBodyServletRequest(httpServletRequest, requestBody);

        if (log.isDebugEnabled()) {
            log.debug("新网关->请求路径：{}，请求参数：\n{}", httpServletRequest.getRequestURI(), requestBody);
        }
        return newHttpServletRequest;
    }


    private HttpServletRequest getNewRequestByOldGateWay(HttpServletRequest httpServletRequest) throws IOException {
        String requestBody = HttpUtil.getRequestBody(httpServletRequest);
        if (StringUtil.isBlank(requestBody)) {
            return httpServletRequest;
        }
        RequestBodyContext requestBodyContext = JsonUtil.jsonToPojo(requestBody, RequestBodyContext.class);
        GiimalRequestContextHolder.setRequestContext(httpServletRequest, requestBodyContext);
        ReconstructionBodyServletRequest newHttpServletRequest = new ReconstructionBodyServletRequest(httpServletRequest, requestBody);
        if (log.isDebugEnabled()) {
            log.debug("旧网关->请求路径：{}，请求参数：\n{}", httpServletRequest.getRequestURI(), requestBody);
        }
        return newHttpServletRequest;
    }*/

}

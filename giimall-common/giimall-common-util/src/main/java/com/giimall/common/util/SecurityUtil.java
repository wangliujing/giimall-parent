/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.util;


import com.giimall.common.model.dto.request.ShopSettings;
import com.giimall.common.model.dto.request.UserInfo;
import com.giimall.common.model.dto.request.XReqeustContext;
import com.giimall.common.util.holder.GiimalRequestContextHolder;

/**
 * 获取当前用户信息
 *
 * @author 白清(侯俊昌)
 * @version Id: SecurityUtil, v1.0.0 2022年03月11日 17:30 白清(侯俊昌) Exp $
 */
public class SecurityUtil {

    /**
     * 获取字符串类型的用户ID
     *
     * @return
     */
    public static UserInfo getLoginUser() {
        return GiimalRequestContextHolder.getLoginUser();
    }

    /**
     * 获取Long类型的用户ID
     *
     * @return
     */
    public static Long getUserId() {
        UserInfo loginUser = getLoginUser();
        if(loginUser != null && StringUtil.isNotBlank(loginUser.getUserId())) {
            return Long.parseLong(loginUser.getUserId());
        }
        return null;
    }

    /**
     * 获取用户名字
     *
     * @return {@link String}
     */
    public static String getUserName() {
        UserInfo loginUser = getLoginUser();
        if(loginUser != null) {
            return loginUser.getUserName();
        }
        return null;
    }

    /**
     * 获取Integer类型的用户ID
     *
     * @return {@link Integer}
     */
    public static Integer getIntegerUserId() {
        UserInfo loginUser = getLoginUser();
        if(loginUser != null && StringUtil.isNotBlank(loginUser.getUserId())) {
            return Integer.parseInt(loginUser.getUserId());
        }
        return null;
    }

    /**
     * 获取店铺ID
     *
     * @return {@link Integer}
     */
    public static Long getShopId() {
        XReqeustContext xRequestContext = GiimalRequestContextHolder.getXRequestContext();
        if(xRequestContext != null && StringUtil.isNotBlank(xRequestContext.getShopId())) {
            return Long.parseLong(xRequestContext.getShopId());
        }
        return null;
    }


    /**
     * 获取店铺信息
     *
     * @return {@link ShopSettings}
     */
    public static ShopSettings getShopSettings() {
        return GiimalRequestContextHolder.getShopSettings();
    }

}
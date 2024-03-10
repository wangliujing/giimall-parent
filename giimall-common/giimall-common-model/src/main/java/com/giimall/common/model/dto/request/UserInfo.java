package com.giimall.common.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.giimall.common.constant.SymbolConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户信息
 *
 * @author wangLiuJing
 * Created on 2019/10/1
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo implements Serializable {

    /** 用户id */
    private String userId = SymbolConstant.EMPTY_STRING;

    /** 用户名 */
    private String userName = SymbolConstant.EMPTY_STRING;

    /** 电子邮件 */
    private String email = SymbolConstant.EMPTY_STRING;

    /** 状态 */
    private String status = SymbolConstant.EMPTY_STRING;

    /** 偏好语言 */
    private String preferredLanguage = SymbolConstant.EMPTY_STRING;

}

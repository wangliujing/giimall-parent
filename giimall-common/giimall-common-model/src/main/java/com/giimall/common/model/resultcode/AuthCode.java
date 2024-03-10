package com.giimall.common.model.resultcode;

/**
 * 权限相关枚举代码
 *
 * @author Administrator
 * Created on 2019/10/5
 */
public enum AuthCode implements IResultCode {

	LOGIN_SUCCESS(ResponseCode.SUCCESS, "登录成功", true),

	LOGOUT_SUCCESS(ResponseCode.SUCCESS, "登出成功", true),

	REFRESH_SUCCESS(ResponseCode.SUCCESS, "刷新成功", true),

	UNAUTHENTICATED(301, "您还未登录", false),

	UNAUTHORISE(302, "权限不足", false),

	USERNAME_OR_PASSWORD_ERROR(303, "用户名或密码错误", false),

	ACCOUNT_DISABLED(304, "登录失败次数过多，账号被禁用", false),

	VERIFICATION_CODE_ERROR(305, "验证码错误", false),

	SESSION_EXPIRATION(306, "未登录或登录过期，请重新登录", false),

	LIMIT_LOGIN(307, "限制登录，请联系管理员解除限制", false),

	PHONE_ERROR(308, "手机号码错误或不存在", false),

	CLIENT_LOGIN_ERROR(309, "客户端账号appid或者秘钥secret错误", false),

	THIRD_PARTY_ACCOUNT_UNBOUND_USER(310, "第三方账号未绑定用户", false),

	LACK_PARAMETER_REDIRECT_URI(311, "缺少redirect_uri参数", false),

	LACK_PARAMETER_CLIENT_ID(312, "缺少client_id参数", false),

	CLIENT_UNSUPPORTED_CODE(313, "客户端不支持code模式登录", false),

	ILLEGAL_CODE(314, "非法code", false),

	REDIRECT_URI_MISMATCH(315, "跳转路径redirect_uri不匹配", false);



	/** 操作代码  */
	private int code;

	/** 提示信息  */
	private String msg;

	/** 响应业务状态 */
	private boolean state;

	AuthCode(int code, String msg, boolean state) {
		this.code = code;
		this.msg = msg;
		this.state = state;
	}

	@Override
	public int code() {
		return code;
	}

	@Override
	public String msg() {
		return msg;
	}

	@Override
	public boolean state() {
		return state;
	}
}

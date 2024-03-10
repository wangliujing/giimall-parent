package com.giimall.common.model.resultcode;


/**
 * 错误代码枚举
 *
 * @author Administrator
 * Created on 2019/10/5
 */
public enum ErrorCode implements IResultCode {

	SERVER_ERROR(ResponseCode.FAILED, "抱歉，系统繁忙，请稍后重试！", false),

	NO_SERVICE_PROVIDE_ERROR(501, "抱歉，系统繁忙，请稍后重试！", false),

	LACK_USER_AGENT(502, "没有携带User-Agent请求头信息！", false),

	SENTINEL_AUTHORITY_EXCEPTION(503, "Sentinel权限异常！", false),

	SENTINEL_DEGRADE_EXCEPTION(504, "Sentinel降级异常！", false),

	SENTINEL_FLOW_EXCEPTION(505, "Sentinel流控异常！", false),

	SENTINEL_PARAM_FLOW_EXCEPTION(506, "Sentinel参数流控异常！", false),

	SENTINEL_SYSTEM_BLOCK_EXCEPTION(507, "Sentinel系统阻塞异常！", false);

	/** 操作代码  */
	private int code;

	/** 提示信息  */
	private String msg;

	/** 响应业务状态 */
	private boolean state;

	ErrorCode(int code, String msg, boolean state) {
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

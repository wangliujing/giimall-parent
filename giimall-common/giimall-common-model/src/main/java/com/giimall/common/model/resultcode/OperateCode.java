package com.giimall.common.model.resultcode;


/**
 * 操作代码枚举
 *
 * @author Administrator
 * Created on 2019/10/5
 */
public enum OperateCode implements IResultCode {

	SUCCESS(ResponseCode.SUCCESS, "操作成功", true),

	FAIL(ResponseCode.FAILED, "操作失败", false);

	/** 操作代码 */
	private int code;

	/** 提示信息 */
	private String msg;

	/** 响应业务状态 */
	private boolean state;

	OperateCode(int code, String msg, boolean state) {
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

package com.giimall.common.exception;


import com.giimall.common.constant.SymbolConstant;
import com.giimall.common.model.resultcode.IResultCode;
import lombok.Data;

import java.text.MessageFormat;

/**
 * Class CommonException ...
 *
 * @author wangLiuJing
 * Created on 2019/12/8
 */
@Data
public class CommonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code = 500;


	public CommonException() {
		super();

	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}


	/**
	 * 支持占位符：java{0}替换测试，{1}行不行，{2}可以的
	 *
	 * @param message of type String
	 * @param cause of type Throwable
	 * @param arguments of type Object...
	 */
	public CommonException(String message, Throwable cause, Object ... arguments) {
		super(MessageFormat.format(message, arguments), cause);

	}

	public CommonException(String message) {
		super(message);
	}


	/**
	 * 支持占位符：java{0}替换测试，{1}行不行，{2}可以的
	 *
	 * @param message of type String
	 * @param arguments of type Object...
	 */
	public CommonException(String message, Object ... arguments) {
		super(MessageFormat.format(message, arguments));
	}


	public CommonException(int code, String message) {
		super(message);
		this.code = code;
	}


	public CommonException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}


	public CommonException(Throwable cause) {
		super(cause);
	}

	public CommonException(IResultCode resultCode) {
		super(resultCode.msg());
		this.code = resultCode.code();
	}

	public CommonException(IResultCode resultCode, Throwable cause) {
		super(resultCode.msg(), cause);
		this.code = resultCode.code();
	}

	public CommonException(IResultCode resultCode, String message, Object ... arguments) {
		super(new StringBuilder().append(resultCode.msg()).append(" ").append(SymbolConstant.OPEN_BRACKET)
				.append(MessageFormat.format(message, arguments)).append(SymbolConstant.CLOSE_BRACKET).toString());
		this.code = resultCode.code();
	}

	public CommonException(IResultCode resultCode, Throwable cause, String message, Object ... arguments) {
		super(new StringBuilder().append(resultCode.msg()).append(" ").append(SymbolConstant.OPEN_BRACKET)
				.append(MessageFormat.format(message, arguments)).append(SymbolConstant.CLOSE_BRACKET).toString(), cause);
		this.code = resultCode.code();
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		// 重写，禁止抓取堆栈信息
		return this;
	}

	@Override
	public String toString() {
		return String.format("%s:%s:%s", code, this.getClass().getName(), this.getMessage());
	}
}

package com.giimall.common.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.giimall.common.exception.CommonException;
import com.giimall.common.model.resultcode.ErrorCode;
import com.giimall.common.model.resultcode.IResultCode;
import com.giimall.common.model.resultcode.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author 王柳敬
 * @version 创建时间：2018年6月8日---上午11:19:16
 * @declare 自定义响应结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardResult<T> implements Serializable {

	/**
	 * 是否成功响应
	 */
	protected boolean state;

	/**
	 * 响应消息
	 */
	protected String msg;

	/**
	 * 响应数据
	 */
	private T data;

	/**
	 * 响应状态码
	 */
	protected int code;


	public StandardResult(IResultCode resultCode, T data) {
		this.code = resultCode.code();
		this.state = resultCode.state();
		this.msg = resultCode.msg();
		this.data = data;
	}

	public StandardResult(Throwable throwable) {
		this.code = ResponseCode.FAILED;
		this.state = false;
		this.msg = StringUtils.isBlank(throwable.getMessage()) ? ErrorCode.SERVER_ERROR.msg(): throwable.getMessage();
	}

	public StandardResult(CommonException commonException) {
		this.code = commonException.getCode();
		this.state = false;
		this.msg = StringUtils.isBlank(commonException.getMessage()) ? ErrorCode.SERVER_ERROR.msg(): commonException
				.getMessage();
	}

	public static <T> StandardResult<T> resultCode(IResultCode resultCode) {
		return new StandardResult(resultCode, null);
	}


	public static <T> StandardResult<T> resultCode(IResultCode resultCode, T object) {
		return new StandardResult(resultCode, object);
	}
}


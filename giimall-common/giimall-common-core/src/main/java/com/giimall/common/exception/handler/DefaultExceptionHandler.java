package com.giimall.common.exception.handler;

import com.giimall.common.exception.CommonException;
import com.giimall.common.model.StandardResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

/**
 * 全局异常处理器
 *
 * @author wangliujing
 * @date 2019/8/2
 */
@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler extends AbstractExceptionHandler {



	/** @see AbstractExceptionHandler#handlerException(Throwable, HttpServletResponse, HttpServletRequest) */
	@Override
	@ExceptionHandler(Throwable.class)
	public StandardResult handlerException(Throwable e, HttpServletResponse response, HttpServletRequest request) {
		return super.handlerException(e, response, request);
	}

	/** @see AbstractExceptionHandler#handlerCommonException(CommonException, HttpServletResponse, HttpServletRequest) */
	@Override
	@ExceptionHandler(CommonException.class)
	public StandardResult handlerCommonException(CommonException e, HttpServletResponse response,
												 HttpServletRequest request) {
		return super.handlerCommonException(e, response, request);
	}

	@ExceptionHandler(AssertionError.class)
	public StandardResult handlerAssertionError(AssertionError e, HttpServletResponse response,
												 HttpServletRequest request) {
		return super.handlerCommonException(new CommonException(e), response, request);
	}

	/** @see AbstractExceptionHandler#handlerValidationException(Exception, HttpServletRequest) */
	@Override
	@ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class, BindException.class})
	public StandardResult handlerValidationException(Exception e, HttpServletRequest request) {
		return super.handlerValidationException(e, request);
	}

}

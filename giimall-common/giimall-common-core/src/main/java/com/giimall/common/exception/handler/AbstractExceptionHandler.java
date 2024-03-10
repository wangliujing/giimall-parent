package com.giimall.common.exception.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.giimall.common.exception.CommonException;
import com.giimall.common.model.StandardResult;
import com.giimall.common.model.resultcode.AuthCode;
import com.giimall.common.model.resultcode.ErrorCode;
import com.giimall.common.model.resultcode.IResultCode;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

/**
 * 异常处理抽象类
 *
 * @author wangLiuJing
 * Created on 2021/9/2
 */
@Slf4j
public class AbstractExceptionHandler {

	/**
	 * 定义map的builder对象，去构建ImmutableMap
	 */
	protected ImmutableMap.Builder<Class<? extends Throwable>, IResultCode> builder = ImmutableMap.builder();

	/**
	 * 定义map，配置异常类型所对应的错误代码
	 */
	protected ImmutableMap<Class<? extends Throwable>, IResultCode> EXCEPTIONS;

	{

		//定义异常类型所对应的错误代码
		///builder.put(AccessDeniedException.class, AuthCode.UNAUTHORISE);
	}

	public void registException(Class<? extends Throwable> clazz, IResultCode resultCode) {
		builder.put(clazz, resultCode);
	}


	/**
	 * 全局Exception处理
	 *
	 * @param e
	 * @param response
	 * @return ExceptionDTO
	 * @author wangliujing
	 * @date 2019/8/5
	 */
	public StandardResult handlerException(Throwable e, HttpServletResponse response, HttpServletRequest request) {
		//记录日志
		log.error("catch exception:{}", e);
		if (EXCEPTIONS == null) {
			// EXCEPTIONS构建成功
			EXCEPTIONS = builder.build();
		}
		// 获取根异常
		Throwable cause = e.getCause();

		if (cause != null) {
			e = cause;
		}

		if (EXCEPTIONS.containsKey(e.getClass())) {
			IResultCode resultCode = EXCEPTIONS.get(e.getClass());
			if (resultCode != null) {
				return StandardResult.resultCode(resultCode);
			} else {
				return new StandardResult(e);
			}
		}

		StandardResult standardResult;
		if (e instanceof CommonException) {
			standardResult = new StandardResult((CommonException) e);
		} else if(e instanceof JsonMappingException){
			standardResult = new StandardResult((JsonMappingException) e);
		} else {
			standardResult = StandardResult.resultCode(ErrorCode.SERVER_ERROR);
		}
		return standardResult;
	}

	/**
	 * 全局CommonException处理
	 *
	 * @param e
	 * @param response
	 * @return ExceptionDTO
	 * @author wangliujing
	 * @date 2019/8/5
	 */
	public StandardResult handlerCommonException(CommonException e, HttpServletResponse response,
												 HttpServletRequest request) {
		//记录日志
		log.error("catch exception:{}", e);
		if (e.getCode() == AuthCode.SESSION_EXPIRATION.code()) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		return new StandardResult(e);
	}


	/**
	 * 全局ValidationException处理
	 *
	 * @param e       of type ValidationException
	 * @param request of type HttpServletRequest
	 * @return StandardResult
	 * @author wangLiuJing
	 * Created on 2021/4/26
	 */
	public StandardResult handlerValidationException(Exception e, HttpServletRequest request) {
		// 记录日志
		log.error("catch exception:{}", e);
		String message = null;
		// 参数校验get
		if(e instanceof ConstraintViolationException){
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
			Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
			Iterator<ConstraintViolation<?>> iterator = violations.iterator();
			if(iterator.hasNext()){
				ConstraintViolation<?> next = iterator.next();
				message = next.getMessage();
				if(log.isDebugEnabled()) {
					message = String.format("[%s] %s", next.getPropertyPath(), message);
				} else {
					log.error(String.format("[%s] %s", next.getPropertyPath(), message));
				}
			}
		}
		// 参数校验post
		if(e instanceof MethodArgumentNotValidException){
			MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
			BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
			FieldError fieldError = bindingResult.getFieldErrors().get(0);
			message = fieldError.getDefaultMessage();
			if(log.isDebugEnabled()) {
				message = String.format("[%s] %s", fieldError.getField(), message);
			} else {
				log.error(String.format("[%s] %s", fieldError.getField(), message));
			}
		}

		if(e instanceof BindException) {
			ObjectError objectError = ((BindException) e).getAllErrors().get(0);
			message = objectError.getDefaultMessage();
			if(log.isDebugEnabled()) {
				message = String.format("[%s] %s", objectError.getObjectName(), message);
			} else {
				log.error(String.format("[%s] %s", objectError.getObjectName(), message));
			}
		}
		return new StandardResult(new CommonException(message));
	}

}

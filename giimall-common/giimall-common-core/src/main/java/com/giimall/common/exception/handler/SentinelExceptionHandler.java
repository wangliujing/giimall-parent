package com.giimall.common.exception.handler;/*
package com.giimall.common.exception.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.giimall.common.model.StandardResult;
import com.giimall.common.model.resultcode.ErrorCode;
import com.giimall.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

*/
/**
 * 集成Sentinel的时候走这个全局处理异常
 *
 * @author wangliujing
 * @date 2019/8/2
 *//*

@RestControllerAdvice
@Slf4j
public class SentinelExceptionHandler extends AbstractExceptionHandler {

    */
/**
     * @see AbstractExceptionHandler#handlerException(Throwable, HttpServletResponse, HttpServletRequest)
     *//*

    @Override
    @ExceptionHandler(Throwable.class)
    public StandardResult handlerException(Throwable e, HttpServletResponse response, HttpServletRequest request) {
        // 降级保护
        if (e.getCause() instanceof BlockException) {
            return getBlockExceptionResult((BlockException) e.getCause(), request);
        }
        return super.handlerException(e, response, request);
    }

    */
/**
     * @see AbstractExceptionHandler#handlerCommonException(CommonException, HttpServletResponse, HttpServletRequest)
     *//*

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

    */
/**
     * @see AbstractExceptionHandler#handlerValidationException(Exception, HttpServletRequest)
     *//*

    @Override
    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public StandardResult handlerValidationException(Exception e, HttpServletRequest request) {
        return super.handlerValidationException(e, request);
    }

    */
/**
     * 获取Sentinel容错降级异常结果
     *
     * @param blockException of type BlockException
     * @return StandardResult
     * @author wangLiuJing
     * Created on 2021/8/31
     *//*

    private StandardResult getBlockExceptionResult(BlockException blockException, HttpServletRequest request) {

        ErrorCode errorCode;
        if (blockException instanceof AuthorityException) {
            errorCode = ErrorCode.SENTINEL_AUTHORITY_EXCEPTION;
        } else if (blockException instanceof DegradeException) {
            errorCode = ErrorCode.SENTINEL_DEGRADE_EXCEPTION;
        } else if (blockException instanceof FlowException) {
            errorCode = ErrorCode.SENTINEL_FLOW_EXCEPTION;
        } else if (blockException instanceof ParamFlowException) {
            errorCode = ErrorCode.SENTINEL_PARAM_FLOW_EXCEPTION;
        } else {
            errorCode = ErrorCode.SENTINEL_SYSTEM_BLOCK_EXCEPTION;
        }
        return StandardResult.resultCode(errorCode);
    }

}
*/

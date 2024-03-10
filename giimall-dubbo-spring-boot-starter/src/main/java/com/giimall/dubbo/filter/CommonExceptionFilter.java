/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.dubbo.filter;

import com.giimall.common.exception.CommonException;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.filter.ExceptionFilter;


/**
 * dobbon 提供方异常全局处理
 * @author wangLiuJing
 * @date 2022/04/13
 */
@Activate(group = CommonConstants.PROVIDER)
public class CommonExceptionFilter extends ExceptionFilter {

    private Logger logger = LoggerFactory.getLogger(CommonExceptionFilter.class);

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        if (appResponse.hasException()) {
            try {
                Throwable exception = appResponse.getException();
                if (exception instanceof CommonException) {
                    logger.error("Got unchecked and undeclared exception which called by "
                            + RpcContext.getServiceContext().getRemoteHost()
                            + ". service: " + invoker.getInterface().getName()
                            + ", method: " + invocation.getMethodName()
                            + ", exception: " + exception.getClass().getName()
                            + ": "
                            + exception.getMessage(), exception);
                    return;
                }
                super.onResponse(appResponse, invoker, invocation);
            } catch (Throwable e) {
                logger.warn("Fail to ExceptionFilter when called by " + RpcContext.getServiceContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
            }
        }
    }

}


/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.dubbo.filter;

import com.giimall.common.constant.SystemConstant;
import com.giimall.common.util.holder.GiimalRequestContextHolder;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * dubbo服务消费者拦截器
 *
 * @author wangLiuJing
 * @version Id: UserInfoProviderFilter, v1.0.0 2022年04月06日 13:50 wangLiuJing Exp $ 
 */
@Activate(group = {CommonConstants.CONSUMER})
public class RequestContextConsumerFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		RpcContextAttachment rpcContextAttachment = RpcContext.getClientAttachment();
		rpcContextAttachment.setAttachment(SystemConstant.REQUEST_CONTEXT,
				GiimalRequestContextHolder.getRequestBodyContext());
		return invoker.invoke(invocation);
	}
}

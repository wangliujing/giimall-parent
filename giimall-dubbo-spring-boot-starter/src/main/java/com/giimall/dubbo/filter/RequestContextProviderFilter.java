/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.dubbo.filter;

import com.giimall.common.constant.SystemConstant;
import com.giimall.common.model.dto.request.RequestBodyContext;
import com.giimall.common.util.holder.GiimalRequestContextHolder;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * dubbo服务提供者拦截器
 *
 * @author wangLiuJing
 * @version Id: UserInfoProviderFilter, v1.0.0 2022年04月06日 13:50 wangLiuJing Exp $ 
 */
@Activate(group = {CommonConstants.PROVIDER})
public class RequestContextProviderFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		try {
			RpcContextAttachment rpcContextAttachment = RpcContext.getServerAttachment();
			GiimalRequestContextHolder.setRequestContext((RequestBodyContext) rpcContextAttachment
					.getObjectAttachment(SystemConstant.REQUEST_CONTEXT));
			return invoker.invoke(invocation);
		} finally {
			// 清空线程防止内存泄漏
			GiimalRequestContextHolder.clearRequestContext();
		}
	}
}

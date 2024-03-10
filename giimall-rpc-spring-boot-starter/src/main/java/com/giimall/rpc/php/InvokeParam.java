/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.rpc.php;

import com.giimall.common.model.dto.request.RequestContext;
import com.giimall.common.util.holder.GiimalRequestContextHolder;
import com.giimall.common.util.JsonUtil;
import lombok.Data;

/**
 * jsonrpc远程调用参数
 * {"jsonrpc":2,"method":"/area/getCountryList","params":[{"level":3,"language":[""]}],"id":1647851922843}
 * @author wangLiuJing
 * @version Id: InvokeParam, v1.0.0 2022年03月21日 18:37 wangLiuJing Exp $ 
 */
@Data
public class InvokeParam {

	private final String id = System.currentTimeMillis() + "";

	private final String jsonrpc = "2.0";

	private String method;

	private Object params;

	private RequestContext context;

	private InvokeParam(String method, Object params){
		this.method = method;
		if(JsonUtil.isJSON(params)) {
			this.params = JsonUtil.jsonToPojo((String) params, Object.class);
		} else {
			this.params = params;
		}
		// 上下文信息，包含店铺用户等信息
		context = GiimalRequestContextHolder.getRequestContext();
	}

	public static InvokeParam getInstance(String method, Object params) {
		return new InvokeParam(method, params);
	}

	@Override
	public String toString() {
		return JsonUtil.objectToJson(this);
	}

}

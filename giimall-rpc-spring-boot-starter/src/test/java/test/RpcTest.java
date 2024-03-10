/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package test;

import com.giimall.common.util.JsonUtil;
import com.giimall.rpc.php.InvokeResult;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: RpcTest, v1.0.0 2022年07月12日 15:58 wangLiuJing Exp $ 
 */
public class RpcTest {

	public static void main(String[] args) {
		String str = "{\n" +
				"    \"jsonrpc\": \"2.0\",\n" +
				"    \"id\": 40,\n" +
				"    \"error\": {\n" +
				"        \"code\": -32601,\n" +
				"        \"message\": \"Method not found.\",\n" +
				"        \"data\": null\n" +
				"    },\n" +
				"    \"context\": {\n" +
				"        \"x-request-context\": [],\n" +
				"        \"x-request-id\": \"\",\n" +
				"        \"Accept-Language-Id\": \"\",\n" +
				"        \"Accept-Currency-Id\": \"\"\n" +
				"    }\n" +
				"}";

		str = "{\n" +
				"    \"jsonrpc\": \"2.0\",\n" +
				"    \"id\": 40,\n" +
				"    \"result\": [\n" +
				"        {\n" +
				"            \"id\": \"738\",\n" +
				"            \"logisticsProviderName\": \"自动化物流添加test\",\n" +
				"            \"isSelfOperated\": 0\n" +
				"        }\n" +
				"    ],\n" +
				"    \"context\": {\n" +
				"        \"x-request-context\": [],\n" +
				"        \"x-request-id\": \"\",\n" +
				"        \"Accept-Language-Id\": \"\",\n" +
				"        \"Accept-Currency-Id\": \"\"\n" +
				"    }\n" +
				"}";
		InvokeResult invokeResult = JsonUtil.jsonToPojo(str, InvokeResult.class);
		System.out.println(invokeResult.getResult());
	}

}

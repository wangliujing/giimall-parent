/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.rpc.php;

import com.giimall.common.constant.MediaTypeConstant;
import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 远程调用php服务工具类
 *
 * @author wangLiuJing
 * @version Id: JsonRpcClient, v1.0.0 2022年03月21日 18:34 wangLiuJing Exp $ 
 */
@Slf4j
public class JsonRpcClient {

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	@Autowired
	private RestTemplate restTemplate;

	public InvokeResult invoke(String serviceName, InvokeParam invokeParam) {
		log.debug("JsonRpc 请求服务：{}, 请求参数：{}", serviceName, invokeParam);
		ServiceInstance choose = loadBalancerClient.choose(serviceName);
		if(choose == null) {
			throw new RuntimeException(String.format("未找到PHP服务实例【%s】", serviceName));
		}
		String url = choose.getUri().toString() + invokeParam.getMethod();
		LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
		header.add(HttpHeaders.CONTENT_TYPE, MediaTypeConstant.APPLICATION_JSON);
		HttpEntity httpEntity = new HttpEntity(invokeParam, header);
		ResponseEntity<InvokeResult> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, InvokeResult.class);

		if (!HttpStatus.OK.equals(exchange.getStatusCode())) {
			RuntimeException runtimeException = new RuntimeException(exchange.getBody().toString());
			log.error("JsonRpc远程调用异常：{}", runtimeException);
			throw runtimeException;
		}
		return exchange.getBody();
	}

}

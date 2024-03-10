/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.rpc.config;


import com.giimall.rpc.php.JsonRpcClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 远程调用自动配置类
 *
 * @author wangLiuJing
 * @version Id: DubboConfig, v1.0.0 2022年05月10日 13:49 wangLiuJing Exp $ 
 */
@SpringBootConfiguration
public class GiimallRpcAutoConfig {

	@Bean
	public JsonRpcClient jsonRpcClient() {
		return new JsonRpcClient();
	}
}

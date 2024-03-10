/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.privilege.listener;

import com.giimall.common.constant.SymbolConstant;
import com.giimall.common.util.HttpUtil;
import com.giimall.common.util.StringUtil;
import com.giimall.privilege.model.dto.ApiNodeDTO;
import com.giimall.privilege.property.SwaggerProperty;
import com.giimall.rpc.php.InvokeParam;
import com.giimall.rpc.php.JsonRpcClient;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: RegistApiAuthority, v1.0.0 2022年07月08日 19:24 wangLiuJing Exp $ 
 */
@Slf4j
public class ApiAuthorityRegister implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private DocumentationCache documentationCache;
	@Autowired
	private ServiceModelToSwagger2Mapper mapper;
	@Autowired
	private JsonRpcClient jsonRpcClient;
	@Autowired
	private SwaggerProperty swaggerProperty;

	private boolean isInvoke;


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(isInvoke) {
			return;
		}

		new Thread(() -> {
			log.info("开始同步权限接口");
			isInvoke = true;
			Documentation documentation = documentationCache.documentationByGroup(Docket.DEFAULT_GROUP_NAME);
			Swagger swagger = mapper.mapDocumentation(documentation);
			Map<String, Path> paths = swagger.getPaths();
			if(paths == null || paths.isEmpty()) {
				return;
			}
			// 同步根路径
			regist(swaggerProperty.getTitle(), null, swaggerProperty.getBasePath(), 1);
			Set<String> set = new HashSet<>(paths.size());
			paths.forEach((url, path) -> {
				List<String> uriParts = HttpUtil.getUriParts(url);
				String secondLevelUrl = StringUtil.join(SymbolConstant.SLASH, uriParts.get(0), SymbolConstant.SLASH,
						uriParts.get(1));
				if(set.add(secondLevelUrl)) {
					// 同步二级路径
					regist(path.getPost().getTags().get(0), swaggerProperty.getBasePath(), secondLevelUrl, 2);
				}
				// 同步三级路径
				regist(path.getPost().getSummary(), secondLevelUrl, url, 3);
			});
			log.info("结束同步权限接口");
		}).start();
	}

	private void regist(String alias, String parentUrl, String path, Integer level) {
		if(swaggerProperty.getAuthCode() == null) {
			return;
		}
		ApiNodeDTO apiNodeDTO = new ApiNodeDTO(alias, parentUrl, path, level, swaggerProperty.getAuthCode().getValue());
		InvokeParam invokeParam = InvokeParam.getInstance("api_node/import", apiNodeDTO);
		jsonRpcClient.invoke("ApiNodeService", invokeParam);
	}
}

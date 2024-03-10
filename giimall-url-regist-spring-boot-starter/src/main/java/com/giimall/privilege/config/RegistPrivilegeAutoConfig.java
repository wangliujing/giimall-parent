package com.giimall.privilege.config;

import com.giimall.common.constant.SymbolConstant;
import com.giimall.privilege.listener.ApiAuthorityRegister;
import com.giimall.privilege.property.SwaggerProperty;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Knife4j 配置类
 *
 * @author wangLiuJing
 * Created on 2019/9/19
 */
@SpringBootConfiguration
@ConditionalOnProperty(name = "knife4j.enable", havingValue = "true")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(SwaggerProperty.class)
public class RegistPrivilegeAutoConfig {

	@Bean
	@Profile({"dev", "test", "pre"})
	public ApiAuthorityRegister apiAuthorityRegister() {
		return new ApiAuthorityRegister();
	}


	@Bean
	@ConditionalOnMissingBean
	public Docket createRestApi(OpenApiExtensionResolver openApiExtensionResolver, SwaggerProperty swaggerProperty) {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo(swaggerProperty)).select()
				// 含有ApiOperation注解的方法加入接口文档
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any()).build().extensions(openApiExtensionResolver
						.buildExtensions(SymbolConstant.EMPTY_STRING));
	}



	/**
	 * Method apiInfo ...
	 * @author wangLiuJing
	 * Created on 2019/12/23
	 * @return ApiInfo
	 */
	private ApiInfo apiInfo(SwaggerProperty swaggerProperty) {
		Contact contact = new Contact("杭州吉喵云网络科技有限公司",
				"https://www.giimall.com/321601278226731008.html", "service@giimall.com");
		return new ApiInfoBuilder().title(swaggerProperty.getTitle()).description(swaggerProperty.getDescription())
				.termsOfServiceUrl("https://www.giimall.com/").contact(contact).version(swaggerProperty.getVersion())
				.build();
	}

	/**
	 * 开启swagger
	 */
	@EnableSwagger2
	public class EnableSwagger {
	}

}


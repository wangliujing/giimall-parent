package com.giimall.common.config;/*
package com.giimall.common.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.command.handler.ModifyParamFlowRulesCommandHandler;
import com.alibaba.csp.sentinel.datasource.*;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.giimall.common.constant.CharsetConstant;
import com.giimall.common.constant.SymbolConstant;
import com.giimall.common.util.FileUtil;
import com.giimall.common.util.spring.SpringContextHelper;
import com.giimall.common.util.SystemUtil;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;


*/
/**
 * 分布式容错保护配置类
 *
 * @author wangLiuJing
 * Created on 2021/9/1
 *//*

@SpringBootConfiguration
@ConditionalOnClass(SentinelResourceAspect.class)
public class SentinelAutoConfig {

	@Bean
	public SentinelResourceAspect sentinelResourceAspect() {
		return new SentinelResourceAspect();
	}

	public static class RulesFileData implements InitFunc {

		public static final String flowRuleFileName = "/flowRules.json";
		public static final String degradeRuleFileName = "/degradeRules.json";
		public static final String systemRuleFileName = "/systemRules.json";
		public static final String authorityRuleFileName = "/authorityRules.json";
		public static final String paramRuleFileName = "/paramRules.json";

		private Converter<String, List<FlowRule>> flowRuleListParser = source -> JSON.parseObject(
				source,
				new TypeReference<List<FlowRule>>() {}
				);

		private Converter<String, List<DegradeRule>> degradeRuleParser = source -> JSON.parseObject(
				source,
				new TypeReference<List<DegradeRule>>() {}
				);

		private Converter<String, List<SystemRule>> systemRuleParser = source -> JSON.parseObject(
				source,
				new TypeReference<List<SystemRule>>() {}
		);

		private Converter<String, List<AuthorityRule>> authorityRuleParser = source -> JSON.parseObject(
				source,
				new TypeReference<List<AuthorityRule>>() {}
				);

		private Converter<String, List<ParamFlowRule>> paramRuleParser = source -> JSON.parseObject(
				source,
				new TypeReference<List<ParamFlowRule>>() {}
				);


		@Override
		public void init() throws Exception {
			String dirPath;

			boolean runInJar = SystemUtil.isRunInJar();
			// 如果运行jar包则在jar的平级目录 sentinel/工程名 增加配置文件
			if (runInJar) {
				String jarDir = new File(ResourceUtils.getURL("classpath:").getPath())
						.getParentFile().getParentFile().getParent();
				StringBuilder sb = new StringBuilder(jarDir.substring(5));
				sb.append("/sentinel");
				dirPath = sb.toString();
			} else {
				// 如果不运行在jar则在根目录 + sentinel/工程名 增加配置文件
				Resource classpathResource = FileUtil.getClasspathResource(SymbolConstant.EMPTY_STRING);
				StringBuilder sb = new StringBuilder(classpathResource.getURL().getPath());
				sb.append("sentinel/");
				sb.append(SpringContextHelper.getApplicationProValue("spring.application.name"));
				dirPath = sb.toString();
			}
			dirPath = URLDecoder.decode(dirPath, CharsetConstant.CHARSET_UTF_8);
			File dirFile = new File(dirPath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			String flowRulePath = dirPath + flowRuleFileName;
			String degradeRulePath = dirPath + degradeRuleFileName;
			String systemRulePath = dirPath + systemRuleFileName;
			String authorityRulePath = dirPath + authorityRuleFileName;
			String paramRulePath = dirPath + paramRuleFileName;
			//创建文件
			this.createFile(flowRulePath);
			this.createFile(degradeRulePath);
			this.createFile(systemRulePath);
			this.createFile(authorityRulePath);
			this.createFile(paramRulePath);

			// 流控规则
			//定义写数据源
			WritableDataSource<List<FlowRule>> flowRuleWDS = new FileWritableDataSource<>(flowRulePath, this::encodeJson);
			WritableDataSourceRegistry.registerFlowDataSource(flowRuleWDS);

			//定义读数据源
			ReadableDataSource<String, List<FlowRule>> flowRuleRDS = new FileRefreshableDataSource<>(flowRulePath, flowRuleListParser);
			FlowRuleManager.register2Property(flowRuleRDS.getProperty());

			//降级规则
			WritableDataSource<List<DegradeRule>> degreadeRuleWDS = new FileWritableDataSource<>(degradeRulePath, this::encodeJson);
			WritableDataSourceRegistry.registerDegradeDataSource(degreadeRuleWDS);

			ReadableDataSource degradeRuleRDS = new FileRefreshableDataSource(degradeRulePath, degradeRuleParser);
			DegradeRuleManager.register2Property(degradeRuleRDS.getProperty());

			//系统规则
			WritableDataSource<List<SystemRule>> systemRuleWDS = new FileWritableDataSource<>(systemRulePath, this::encodeJson);
			WritableDataSourceRegistry.registerSystemDataSource(systemRuleWDS);

			ReadableDataSource systemRuleRDS = new FileRefreshableDataSource(systemRulePath, systemRuleParser);
			SystemRuleManager.register2Property(systemRuleRDS.getProperty());

			//授权规则
			WritableDataSource<List<AuthorityRule>> authorityRuleWDS = new FileWritableDataSource<>(authorityRulePath, this::encodeJson);
			WritableDataSourceRegistry.registerAuthorityDataSource(authorityRuleWDS);

			ReadableDataSource authorityRuleRDS = new FileRefreshableDataSource(authorityRulePath, authorityRuleParser);
			AuthorityRuleManager.register2Property(authorityRuleRDS.getProperty());

			//参数规则
			WritableDataSource<List<ParamFlowRule>> paramRuleWDS = new FileWritableDataSource<>(paramRulePath, this::encodeJson);
			ModifyParamFlowRulesCommandHandler.setWritableDataSource(paramRuleWDS);

			ReadableDataSource paramRuleRDS = new FileRefreshableDataSource(paramRulePath, paramRuleParser);
			ParamFlowRuleManager.register2Property(paramRuleRDS.getProperty());
		}

		private void createFile(String filePath) throws IOException {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
		}

		private <T> String encodeJson(T t) {
			return JSON.toJSONString(t);
		}
	}
}
*/

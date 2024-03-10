package com.giimall.generator.config;

import com.giimall.common.constant.SymbolConstant;
import com.giimall.common.exception.CommonException;
import com.giimall.common.util.StringUtil;
import lombok.Builder;
import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 代码生产配置属性
 *
 * @author wangLiuJing
 * Created on 2022/3/8
 */
@Data
public class GeneratorProperty {

	/** 数据源  */
	private DataSource dataSource;
	/** 父包名  */
	private String parentPackage = "com.giimall";
	/** 模块名  */
	private String moduleName;
	/** 输出目录  */
	private String outputDir = "D://mybatis-pluse";
	/** 作者  */
	private String author;
	/** 表前缀  */
	private String tablePrefix = "";
	/** 表名 多个逗号隔开  */
	private String tableNames = "all";
	/** 数据库乐观锁版本控制字段  */
	private String versionColumnName;

	private GeneratorProperty(){
		InputStream in = GeneratorProperty.class.getClassLoader().getResourceAsStream("application.yaml");
		Map<String, Object> config = new Yaml().loadAs(in, Map.class);
		Map<String, Object> generator = (Map<String, Object>) config.get("generator");
		Map<String, Object> dataSource = (Map<String, Object>) generator.get("data-source");
		this.dataSource = DataSource.builder()
				.url(StringUtil.valueOf(dataSource.get("url")))
				.username(StringUtil.valueOf(dataSource.get("username")))
				.password(StringUtil.valueOf(dataSource.get("password"))).build();
		String parentPackage = (String) generator.get("parent-package");
		if(StringUtil.isNotBlank(parentPackage)){
			this.parentPackage = parentPackage;
		}
		String moduleName = (String) generator.get("module-name");
		if(StringUtil.isNotBlank(moduleName)){
			this.moduleName = moduleName;
		}
		String outputDir = (String) generator.get("output-dir");
		if(StringUtil.isNotBlank(outputDir)){
			this.outputDir = outputDir;
		}
		String author = (String) generator.get("author");
		if(StringUtil.isNotBlank(author)){
			this.author = author;
		}
		String tablePrefix = (String) generator.get("table-prefix");
		if(StringUtil.isNotBlank(tablePrefix)){
			this.tablePrefix = tablePrefix;
		}

		String tableNames = (String) generator.get("table-names");
		if(StringUtil.isNotBlank(tableNames)){
			this.tableNames = tableNames;
		}

		String versionColumnName = (String) generator.get("version-column-name");
		if(StringUtil.isNotBlank(versionColumnName)){
			this.versionColumnName = versionColumnName;
		}
	}

	/**
	 * 初始化属性对象
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 * @return GeneratorProperty
	 */
	public static GeneratorProperty init(){
		return new GeneratorProperty();
	}

	/**
	 * 属性校验
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 */
	public void checkProperty(){
		if(dataSource == null){
			throw new CommonException("数据源不能为空");
		}
		dataSource.checkProperty();
		if(StringUtil.isBlank(moduleName)){
			throw new CommonException("模块名不能为空");
		}
		if(StringUtil.isBlank(author)){
			throw new CommonException("作者不能为空");
		}
	}

	/**
	 * 处理 all 情况
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 *
	 * @return List
	 */
	public List<String> getTableNames() {
		// 如果为空则为所有的表生成代码
		if(StringUtil.isBlank(tableNames)){
			return Collections.emptyList();
		}
		return "all".equals(tableNames) ? Collections.emptyList() : Arrays.asList(tableNames.split(",|，"));
	}

	/**
	 * 获取实体包名
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 * @return the entityPackageName (type String) of this GeneratorProperty object.
	 */
	public String getEntityPackageName(){
		return parentPackage + SymbolConstant.POINT + moduleName + ".api.model.entity";
	}

	/**
	 * 获取服务接口包名
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 * @return the servicePackageName (type String) of this GeneratorProperty object.
	 */
	public String getServicePackageName(){
		return parentPackage + SymbolConstant.POINT + moduleName + ".api.service";
	}

	/**
	 * 获取服务实现包名
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 * @return the serviceImplPackageName (type String) of this GeneratorProperty object.
	 */
	public String getServiceImplPackageName(){
		return parentPackage + SymbolConstant.POINT + moduleName + ".service";
	}

	/**
	 * 获取控制器包名
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 * @return the controllerPackageName (type String) of this GeneratorProperty object.
	 */
	public String getControllerPackageName(){
		return parentPackage + SymbolConstant.POINT + moduleName + ".controller";
	}

	/**
	 * 获取Mapper包名.
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 * @return the mapperPackageName (type String) of this GeneratorProperty object.
	 */
	public String getMapperPackageName(){
		return parentPackage + SymbolConstant.POINT + moduleName + ".mapper";
	}

	/**
	 * 获取MapperXml输出路径
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 * @return the mapperXmlOutPutPath (type String) of this GeneratorProperty object.
	 */
	public String getMapperXmlOutputPath(){
		return outputDir + "/mapper";
	}

	/**
	 * 数据库实体输出路径
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 * @return the entityOutPutPath (type String) of this GeneratorProperty object.
	 */
	public String getEntityOutputPath(){
		String pathFragment = StringUtil.join(moduleName.split("\\."), SymbolConstant.SLASH);
		return outputDir + "/com/giimall/" + pathFragment + "/api/model/entity";
	}

	/**
	 * 获取service接口输出路径
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 * @return the serviceOutputPath (type String) of this GeneratorProperty object.
	 */
	public String getServiceOutputPath(){
		String pathFragment = StringUtil.join(moduleName.split("\\."), SymbolConstant.SLASH);
		return outputDir + "/com/giimall/" + pathFragment + "/api/service";
	}

	@Data
	@Builder
	public static class DataSource {

		private String url;

		private String username;

		private String password;


		/**
		 * 属性校验
		 * @author wangLiuJing
		 * Created on 2022/3/8
		 */
		public void checkProperty(){
			if(StringUtil.isBlank(url)){
				throw new CommonException("数据库url不能为空");
			}

			if(StringUtil.isBlank(username)){
				throw new CommonException("数据库用户名不能为空");
			}

			if(StringUtil.isBlank(password)){
				throw new CommonException("数据库密码不能为空");
			}
		}
	}

}

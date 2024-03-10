package com.giimall.generator.core;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.giimall.common.constant.MybatisConstant;
import com.giimall.generator.config.GeneratorProperty;
import com.giimall.generator.convert.CustomMysqlTypeConvert;
import com.giimall.generator.convert.CustomOracleTypeConvert;
import com.giimall.mybatis.mapper.GiimallMapper;
import com.giimall.mybatis.service.IGiimallService;
import com.giimall.mybatis.service.impl.GiimallServiceImpl;

import java.util.HashMap;

/**
 * Class MpGenerator ...
 *
 * @author wangLiuJing
 * Created on 2019/9/14
 */
public class MpGenerator {

	public void execute() {
		GeneratorProperty generatorProperty = GeneratorProperty.init();
		generatorProperty.checkProperty();
		GeneratorProperty.DataSource dataSource = generatorProperty.getDataSource();

		FastAutoGenerator.create(getDataSourceConfigBuilder(dataSource))
				.globalConfig(builder -> {
					// 设置作者
					builder.author(generatorProperty.getAuthor())
							// 覆盖已生成文件
							.fileOverride()
							.enableSwagger()
							// 指定输出目录
							.outputDir(generatorProperty.getOutputDir());
				})
				.packageConfig(builder -> {
					// 设置父包名
					builder.parent(null)
							// 设置父包模块名
							.moduleName(null)
							.entity(generatorProperty.getEntityPackageName())
							.service(generatorProperty.getServicePackageName())
							.serviceImpl(generatorProperty.getServiceImplPackageName())
							.controller(generatorProperty.getControllerPackageName())
							.mapper(generatorProperty.getMapperPackageName())
							.pathInfo(new HashMap<OutputFile, String>(10) {
								{
									put(OutputFile.mapperXml, generatorProperty.getMapperXmlOutputPath());
									put(OutputFile.entity, generatorProperty.getEntityOutputPath());
									put(OutputFile.service, generatorProperty.getServiceOutputPath());
								}
							}); // 设置mapperXml生成路径
				})
				.strategyConfig(builder -> {
					// 设置过滤表前缀
					builder.addInclude(generatorProperty.getTableNames())
							.addTablePrefix(generatorProperty.getTablePrefix())
							.controllerBuilder()
							.enableRestStyle()
							.serviceBuilder()
							.superServiceClass(IGiimallService.class)
							.superServiceImplClass(GiimallServiceImpl.class)
							.entityBuilder()
							.enableColumnConstant()
							.versionColumnName(generatorProperty.getVersionColumnName())
							.formatFileName("%sEntity")
							.enableLombok()
							.logicDeleteColumnName("deleted")
							// 开启tableField注解
							.enableTableFieldAnnotation()
							.addTableFills(new Property(MybatisConstant.DELETED, FieldFill.INSERT),
									new Property(MybatisConstant.CREATED_AT, FieldFill.INSERT),
									new Property(MybatisConstant.UPDATED_AT, FieldFill.INSERT_UPDATE))
							.mapperBuilder().superClass(GiimallMapper.class);

				}).execute();
	}

	/**
	 * 获取数据库配置构建类
	 * @author wangLiuJing
	 * Created on 2022/3/11
	 *
	 * @param dataSource of type DataSource
	 * @return Builder
	 */
	private DataSourceConfig.Builder getDataSourceConfigBuilder(GeneratorProperty.DataSource dataSource){

		DataSourceConfig.Builder builder = new DataSourceConfig.Builder(dataSource.getUrl(), dataSource.getUsername(),
				dataSource.getPassword());

		if (DbType.MYSQL.equals(builder.build().getDbType())) {
			builder.typeConvert(new CustomMysqlTypeConvert());
		} else if (DbType.ORACLE.equals(builder.build().getDbType())) {
			builder.typeConvert(new CustomOracleTypeConvert());
		}
		return builder;
	}
}
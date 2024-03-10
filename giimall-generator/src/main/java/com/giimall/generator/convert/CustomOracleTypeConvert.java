package com.giimall.generator.convert;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.giimall.generator.constant.OracleColumnTypeConstant;

/**
 * 自定义数据库列对应的java类型
 *
 * @author wangLiuJing
 * Created on 2019/9/14
 */
public class CustomOracleTypeConvert extends OracleTypeConvert {
	@Override
	public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
		// 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
		switch (fieldType) {
			case OracleColumnTypeConstant.NUMBER:
				return DbColumnType.LONG;
			case OracleColumnTypeConstant.DATE:
				return DbColumnType.DATE;
			default:
				return super.processTypeConvert(globalConfig, fieldType);
		}
	}
}

package com.giimall.common.config;

import com.giimall.common.model.bo.SnowflakeSequence;
import com.giimall.common.property.SnowflakeSequenceProperties;
import com.giimall.common.util.NumberUtil;
import org.springframework.context.annotation.Bean;

/**
 * 雪花序列自动配置类
 *
 * @author wangLiuJing
 * Created on 2019/12/26
 */
public class SnowflakeSequenceAutoConfig {

	@Bean
	public SnowflakeSequence snowflakeSequence(SnowflakeSequenceProperties snowflakeSequenceProperties){
		Long dataCenterId = snowflakeSequenceProperties.getDataCenterId();
		if(dataCenterId == null){
			dataCenterId = ((Integer) NumberUtil.randomNextInt(4)).longValue();
		}
		return new SnowflakeSequence(dataCenterId);
	}
}

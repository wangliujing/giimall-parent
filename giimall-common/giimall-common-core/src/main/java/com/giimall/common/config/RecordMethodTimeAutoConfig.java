package com.giimall.common.config;

import com.giimall.common.aspect.RecordMethodTimeAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * Class RecordMethodTimeAspectConfig ...
 *
 * @author wangLiuJing
 * Created on 2019/11/7
 */
@SpringBootConfiguration
@ConditionalOnClass(ProceedingJoinPoint.class)
public class RecordMethodTimeAutoConfig {

	@Bean
	public RecordMethodTimeAspect recordMethodTimeAspect() {
		return new RecordMethodTimeAspect();
	}
}

package com.giimall.common.aspect;

import com.giimall.common.annotation.RecordMethodTime;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 记录方法执行时间切面
 *
 * @author wangLiuJing
 * Created on 2019/11/6
 */
@Aspect
@Slf4j
public class RecordMethodTimeAspect {


	@Around(value = "@annotation(com.giimall.common.annotation.RecordMethodTime) && @annotation(recordMethodTime)")
	@SneakyThrows
	public Object recordMethodTime(ProceedingJoinPoint joinPoint, RecordMethodTime recordMethodTime) {
		long startTime = 0;
		if (log.isDebugEnabled()) {
			startTime = System.currentTimeMillis();
		}

		Object proceed = joinPoint.proceed();

		if (log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder(recordMethodTime.msg());
			sb.append("耗时：");
			sb.append(System.currentTimeMillis() - startTime);
			log.debug(sb.toString());
		}

		return proceed;
	}
}

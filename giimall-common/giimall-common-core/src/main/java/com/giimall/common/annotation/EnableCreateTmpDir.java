package com.giimall.common.annotation;

import com.giimall.common.initialize.TmpDirectryCreate;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启自动生成文件下载自定义目录
 *
 * @author zhanghao
 * Created on 2021/2/23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({TmpDirectryCreate.class})
public @interface EnableCreateTmpDir {
}

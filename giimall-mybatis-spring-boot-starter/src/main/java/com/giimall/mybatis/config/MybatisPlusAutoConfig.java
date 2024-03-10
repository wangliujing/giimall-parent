package com.giimall.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.giimall.mybatis.handler.UpdateAndCreateTimeMetaObjectHandler;
import com.giimall.mybatis.injector.GiimallSqlInjector;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author 王柳敬
 * @version 创建时间：2018年8月13日---下午9:21:10
 clare MybatisPlus配置
 */
@SpringBootConfiguration
@ConditionalOnClass(MybatisConfiguration.class)
public class MybatisPlusAutoConfig {

	@SpringBootConfiguration
	@ConditionalOnClass(PaginationInnerInterceptor.class)
	public class PaginationInnerInterceptorAutoConfig {
		/**
		 * mybatis-plus分页插件
		 *
		 * @return
		 */
		@Bean
		@ConditionalOnMissingBean
		public MybatisPlusInterceptor mybatisPlusInterceptor() {
			MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
			interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
			return interceptor;
		}
	}


	/**
	 * 自动注入更新或创建时间
	 * @author wangLiuJing
	 * Created on 2022/3/8
	 * @return MetaObjectHandler
	 */
	@Bean
	@ConditionalOnMissingBean
	public MetaObjectHandler metaObjectHandler(){
		return new UpdateAndCreateTimeMetaObjectHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public ISqlInjector sqlInjector() {
		return new GiimallSqlInjector();
	}

}


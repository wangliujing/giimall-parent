package com.giimall.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.giimall.common.constant.Deleted;
import com.giimall.common.constant.MybatisConstant;
import com.giimall.common.util.DateUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 创建时间，更新时间自动注入
 *
 * @author wangLiuJing
 * Created on 2022/3/8
 */
public class UpdateAndCreateTimeMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, MybatisConstant.CREATED_AT, Date.class, DateUtil.getNowDate());
		this.strictInsertFill(metaObject, MybatisConstant.UPDATED_AT, Date.class, DateUtil.getNowDate());
		this.setFieldValByName(MybatisConstant.DELETED, Deleted.FLASE, metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictUpdateFill(metaObject, MybatisConstant.UPDATED_AT, Date.class, DateUtil.getNowDate());
	}
}

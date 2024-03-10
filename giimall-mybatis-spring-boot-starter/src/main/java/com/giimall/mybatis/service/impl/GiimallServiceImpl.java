/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.giimall.mybatis.mapper.GiimallMapper;
import com.giimall.mybatis.service.IGiimallService;

import java.util.Map;
import java.util.function.Function;

/**
 * 服务基类
 *
 * @author wangLiuJing
 * @version Id: BaseServcieImpl, v1.0.0 2022年06月23日 13:45 wangLiuJing Exp $ 
 */
public class GiimallServiceImpl<M extends GiimallMapper<T>, T> extends ServiceImpl<M, T> implements IGiimallService<T> {

	@Override
	public T getOneIgnoreLogic(Wrapper<T> queryWrapper, boolean throwEx) {
		if (throwEx) {
			return baseMapper.selectOneIgnoreLogic(queryWrapper);
		}
		return SqlHelper.getObject(log, baseMapper.selectListIgnoreLogic(queryWrapper));
	}

	@Override
	public Map<String, Object> getMapIgnoreLogic(Wrapper<T> queryWrapper) {
		return SqlHelper.getObject(log, baseMapper.selectMapsIgnoreLogic(queryWrapper));
	}

	@Override
	public <V> V getObjIgnoreLogic(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
		return SqlHelper.getObject(log, listObjsIgnoreLogic(queryWrapper, mapper));
	}
}

/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.giimall.mybatis.injector.method.*;
import com.giimall.mybatis.mapper.GiimallMapper;

import java.util.List;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: GiimallSqlInjector, v1.0.0 2022年06月23日 11:19 wangLiuJing Exp $ 
 */
public class GiimallSqlInjector extends DefaultSqlInjector {

	@Override
	public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
		List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
		if(GiimallMapper.class.isAssignableFrom(mapperClass)) {
			if (tableInfo.havePK()) {
				methodList.add(new SelectByIdIgnoreLogic());
				methodList.add(new SelectBatchByIdsIgnoreLogic());
			}
			methodList.add(new SelectByMapIgnoreLogic());
			methodList.add(new SelectCountIgnoreLogic());
			methodList.add(new SelectListIgnoreLogic());
			methodList.add(new SelectMapsIgnoreLogic());
			methodList.add(new SelectMapsPageIgnoreLogic());
			methodList.add(new SelectObjsIgnoreLogic());
			methodList.add(new SelectPageIgnoreLogic());
		}
		return methodList;
	}
}

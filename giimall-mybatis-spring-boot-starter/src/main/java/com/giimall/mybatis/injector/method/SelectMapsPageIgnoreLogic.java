/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.mybatis.injector.method;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.giimall.mybatis.injector.enums.SqlMethodIgnoreLogic;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Map;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: SelectMapsPageIgnoreLogic, v1.0.0 2022年06月23日 10:31 wangLiuJing Exp $ 
 */
public class SelectMapsPageIgnoreLogic extends AbstractMethodIgnoreLogic {

	public SelectMapsPageIgnoreLogic() {
		super(SqlMethodIgnoreLogic.SELECT_MAPS_PAGE_IGNORE_LOGIC);
	}

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		String sql = String.format(sqlMethodIgnoreLogic.getSql(), sqlFirst(), sqlSelectColumns(tableInfo, true),
				tableInfo.getTableName(), sqlWhereEntityWrapper(true, tableInfo), sqlOrderBy(tableInfo),
				sqlComment());
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
		return this.addSelectMappedStatementForOther(mapperClass, methodName, sqlSource, Map.class);
	}
}

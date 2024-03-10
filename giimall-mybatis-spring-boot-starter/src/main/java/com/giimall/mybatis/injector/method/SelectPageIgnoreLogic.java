/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.mybatis.injector.method;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.giimall.mybatis.injector.enums.SqlMethodIgnoreLogic;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: SelectPageIgnoreLogic, v1.0.0 2022年06月23日 10:35 wangLiuJing Exp $ 
 */
public class SelectPageIgnoreLogic extends AbstractMethodIgnoreLogic {

	public SelectPageIgnoreLogic() {
		super(SqlMethodIgnoreLogic.SELECT_PAGE_IGNORE_LOGIC);
	}

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		String sql = String.format(sqlMethodIgnoreLogic.getSql(), sqlFirst(), sqlSelectColumns(tableInfo, true),
				tableInfo.getTableName(), sqlWhereEntityWrapper(true, tableInfo), sqlOrderBy(tableInfo),
				sqlComment());
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
		return this.addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
	}
}

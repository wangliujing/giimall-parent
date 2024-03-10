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
 * @version Id: SelectByMapIgnoreLogic, v1.0.0 2022年06月23日 10:30 wangLiuJing Exp $ 
 */
public class SelectByMapIgnoreLogic extends AbstractMethodIgnoreLogic {

	public SelectByMapIgnoreLogic() {
		super(SqlMethodIgnoreLogic.SELECT_BY_MAP_IGNORE_LOGIC);
	}

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		String sql = String.format(sqlMethodIgnoreLogic.getSql(), sqlSelectColumns(tableInfo, false),
				tableInfo.getTableName(), sqlWhereByMap(tableInfo));
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Map.class);
		return this.addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
	}

}

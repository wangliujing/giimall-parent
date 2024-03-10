/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.mybatis.injector.method;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.giimall.mybatis.injector.enums.SqlMethodIgnoreLogic;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: SelectByIdIgnoreLogic, v1.0.0 2022年06月22日 19:50 wangLiuJing Exp $ 
 */
public class SelectByIdIgnoreLogic extends AbstractMethodIgnoreLogic {

	public SelectByIdIgnoreLogic() {
		super(SqlMethodIgnoreLogic.SELECT_BY_ID_IGNORE_LOGIC);
	}

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		SqlSource sqlSource = new RawSqlSource(configuration, String.format(sqlMethodIgnoreLogic.getSql(),
				sqlSelectColumns(tableInfo, false),
				tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty()), Object.class);
		return this.addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
	}
}

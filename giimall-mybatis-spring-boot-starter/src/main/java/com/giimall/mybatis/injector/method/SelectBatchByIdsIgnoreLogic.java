/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.mybatis.injector.method;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.giimall.mybatis.injector.enums.SqlMethodIgnoreLogic;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: SelectBatchByIdsIgnoreLogic, v1.0.0 2022年06月22日 19:51 wangLiuJing Exp $ 
 */
public class SelectBatchByIdsIgnoreLogic extends AbstractMethodIgnoreLogic {

	public SelectBatchByIdsIgnoreLogic() {
		super(SqlMethodIgnoreLogic.SELECT_BATCH_BY_IDS_IGNORE_LOGIC);
	}

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {

		SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(sqlMethodIgnoreLogic.getSql(),
				sqlSelectColumns(tableInfo, false), tableInfo.getTableName(), tableInfo.getKeyColumn(),
				SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA)),
				Object.class);
		return addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
	}
}

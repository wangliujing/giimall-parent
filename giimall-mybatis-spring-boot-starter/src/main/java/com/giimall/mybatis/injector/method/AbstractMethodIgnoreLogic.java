/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.mybatis.injector.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.giimall.mybatis.injector.enums.SqlMethodIgnoreLogic;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: AbstractMethodIgnoreLogic, v1.0.0 2022年06月23日 11:08 wangLiuJing Exp $ 
 */
public abstract class AbstractMethodIgnoreLogic extends AbstractMethod {

	protected SqlMethodIgnoreLogic sqlMethodIgnoreLogic;

	protected AbstractMethodIgnoreLogic(SqlMethodIgnoreLogic sqlMethodIgnoreLogic) {
		super(sqlMethodIgnoreLogic.getMethod());
		this.sqlMethodIgnoreLogic = sqlMethodIgnoreLogic;
	}

	@Override
	public String sqlWhereEntityWrapper(boolean newLine, TableInfo table) {
		String sqlScript = table.getAllSqlWhere(false, true, WRAPPER_ENTITY_DOT);
		sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER_ENTITY), true);
		sqlScript += NEWLINE;
		sqlScript += SqlScriptUtils.convertIf(String.format(SqlScriptUtils.convertIf(" AND", String.format("%s and %s", WRAPPER_NONEMPTYOFENTITY, WRAPPER_NONEMPTYOFNORMAL), false) + " ${%s}", WRAPPER_SQLSEGMENT),
				String.format("%s != null and %s != '' and %s", WRAPPER_SQLSEGMENT, WRAPPER_SQLSEGMENT,
						WRAPPER_NONEMPTYOFWHERE), true);
		sqlScript = SqlScriptUtils.convertWhere(sqlScript) + NEWLINE;
		sqlScript += SqlScriptUtils.convertIf(String.format(" ${%s}", WRAPPER_SQLSEGMENT),
				String.format("%s != null and %s != '' and %s", WRAPPER_SQLSEGMENT, WRAPPER_SQLSEGMENT,
						WRAPPER_EMPTYOFWHERE), true);
		sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER), true);
		return newLine ? NEWLINE + sqlScript : sqlScript;
	}

	@Override
	public String sqlWhereByMap(TableInfo table) {
		String sqlScript = SqlScriptUtils.convertChoose("v == null", " ${k} IS NULL ",
				" ${k} = #{v} ");
		sqlScript = SqlScriptUtils.convertForeach(sqlScript, COLUMN_MAP, "k", "v", "AND");
		sqlScript = SqlScriptUtils.convertWhere(sqlScript);
		sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null and !%s", COLUMN_MAP,
				COLUMN_MAP_IS_EMPTY), true);
		return sqlScript;
	}
}

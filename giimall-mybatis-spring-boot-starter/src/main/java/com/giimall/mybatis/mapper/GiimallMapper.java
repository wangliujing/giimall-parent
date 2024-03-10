/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Mapper 基类
 *
 * @author wangLiuJing
 * @version Id: GiimallBaseMapper, v1.0.0 2022年06月23日 11:31 wangLiuJing Exp $ 
 */
public interface GiimallMapper<T> extends BaseMapper<T> {


	/**
	 * 根据 ID 查询 （忽略逻辑删除条件）
	 *
	 * @param id 主键ID
	 */
	T selectByIdIgnoreLogic(Serializable id);

	/**
	 * 查询（根据ID 批量查询） （忽略逻辑删除条件）
	 *
	 * @param idList 主键ID列表(不能为 null 以及 empty)
	 */
	List<T> selectBatchIdsIgnoreLogic(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);

	/**
	 * 查询（根据 columnMap 条件） （忽略逻辑删除条件）
	 *
	 * @param columnMap 表字段 map 对象
	 */
	List<T> selectByMapIgnoreLogic(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);



	/**
	 * 根据 entity 条件，查询一条记录 （忽略逻辑删除条件）
	 * <p>查询一条记录，例如 qw.last("limit 1") 限制取一条记录, 注意：多条数据会报异常</p>
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	default T selectOneIgnoreLogic(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
		List<T> ts = this.selectListIgnoreLogic(queryWrapper);
		if (CollectionUtils.isNotEmpty(ts)) {
			if (ts.size() != 1) {
				throw ExceptionUtils.mpe("One record is expected, but the query result is multiple records");
			}
			return ts.get(0);
		}
		return null;
	}


	/**
	 * 根据 Wrapper 条件，判断是否存在记录 （忽略逻辑删除条件）
	 *
	 * @param queryWrapper 实体对象封装操作类
	 * @return
	 */
	default boolean existsIgnoreLogic(Wrapper<T> queryWrapper) {
		Long count = this.selectCountIgnoreLogic(queryWrapper);
		return null != count && count > 0;
	}


	/**
	 * 根据 Wrapper 条件，查询总记录数 （忽略逻辑删除条件）
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	Long selectCountIgnoreLogic(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 entity 条件，查询全部记录 （忽略逻辑删除条件）
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	List<T> selectListIgnoreLogic(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 Wrapper 条件，查询全部记录 （忽略逻辑删除条件）
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	List<Map<String, Object>> selectMapsIgnoreLogic(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 Wrapper 条件，查询全部记录 （忽略逻辑删除条件）
	 * <p>注意： 只返回第一个字段的值</p>
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	List<Object> selectObjsIgnoreLogic(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 entity 条件，查询全部记录（并翻页） （忽略逻辑删除条件）
	 *
	 * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	<P extends IPage<T>> P selectPageIgnoreLogic(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 Wrapper 条件，查询全部记录（并翻页） （忽略逻辑删除条件）
	 *
	 * @param page         分页查询条件
	 * @param queryWrapper 实体对象封装操作类
	 */
	<P extends IPage<Map<String, Object>>> P selectMapsPageIgnoreLogic(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}

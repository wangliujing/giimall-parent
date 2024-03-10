/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.giimall.mybatis.mapper.GiimallMapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 服务基类接口
 *
 * @author wangLiuJing
 * @version Id: IGiimallService, v1.0.0 2022年06月23日 13:44 wangLiuJing Exp $ 
 */
public interface IGiimallService<T> extends IService<T> {


	/**
	 * 根据 ID 查询 （忽略逻辑删除条件）
	 *
	 * @param id 主键ID
	 */
	default T getByIdIgnoreLogic(Serializable id) {
		return getBaseMapper().selectByIdIgnoreLogic(id);
	}


	/**
	 * 查询（根据ID 批量查询）（忽略逻辑删除条件）
	 *
	 * @param idList 主键ID列表
	 */
	default List<T> listByIdsIgnoreLogic(Collection<? extends Serializable> idList) {
		return getBaseMapper().selectBatchIdsIgnoreLogic(idList);
	}


	/**
	 * 查询（根据 columnMap 条件）（忽略逻辑删除条件）
	 *
	 * @param columnMap 表字段 map 对象
	 */
	default List<T> listByMapIgnoreLogic(Map<String, Object> columnMap) {
		return getBaseMapper().selectByMapIgnoreLogic(columnMap);
	}


	/**
	 * 根据 Wrapper，查询一条记录  （忽略逻辑删除条件）
	 * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")</p>
	 *
	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
	 */
	default T getOneIgnoreLogic(Wrapper<T> queryWrapper) {
		return getOneIgnoreLogic(queryWrapper, true);
	}

	/**
	 * 根据 Wrapper，查询一条记录  （忽略逻辑删除条件）
	 *
	 * @param queryWrapper
	 * @param throwEx
	 * @return {@link T}
	 */
	T getOneIgnoreLogic(Wrapper<T> queryWrapper, boolean throwEx);


	/**
	 * 根据 Wrapper，查询一条记录 （忽略逻辑删除条件）
	 *
	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
	 */
	Map<String, Object> getMapIgnoreLogic(Wrapper<T> queryWrapper);


	/**
	 * 根据 Wrapper，查询一条记录 （忽略逻辑删除条件）
	 *
	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
	 * @param mapper       转换函数
	 */
	<V> V getObjIgnoreLogic(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);


	/**
	 * 查询总记录数 （忽略逻辑删除条件）
	 *
	 * @see Wrappers#emptyWrapper()
	 */
	default long countIgnoreLogic() {
		return countIgnoreLogic(Wrappers.emptyWrapper());
	}


	/**
	 * 根据 Wrapper 条件，查询总记录数 （忽略逻辑删除条件）
	 *
	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
	 */
	default long countIgnoreLogic(Wrapper<T> queryWrapper) {
		return SqlHelper.retCount(getBaseMapper().selectCountIgnoreLogic(queryWrapper));
	}


	/**
	 * 查询列表 （忽略逻辑删除条件）
	 *
	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
	 */
	default List<T> listIgnoreLogic(Wrapper<T> queryWrapper) {
		return getBaseMapper().selectListIgnoreLogic(queryWrapper);
	}

	/**
	 * 查询所有 （忽略逻辑删除条件）
	 *
	 * @see Wrappers#emptyWrapper()
	 */
	default List<T> listIgnoreLogic() {
		return listIgnoreLogic(Wrappers.emptyWrapper());
	}


	/**
	 * 翻页查询 （忽略逻辑删除条件）
	 *
	 * @param page         翻页对象
	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
	 */
	default <E extends IPage<T>> E pageIgnoreLogic(E page, Wrapper<T> queryWrapper) {
		return getBaseMapper().selectPageIgnoreLogic(page, queryWrapper);
	}


	/**
	 * 无条件翻页查询 （忽略逻辑删除条件）
	 *
	 * @param page 翻页对象
	 * @see Wrappers#emptyWrapper()
	 */
	default <E extends IPage<T>> E pageIgnoreLogic(E page) {
		return pageIgnoreLogic(page, Wrappers.emptyWrapper());
	}


	/**
	 * 查询列表（忽略逻辑删除条件）
	 *
	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
	 */
	default List<Map<String, Object>> listMapsIgnoreLogic(Wrapper<T> queryWrapper) {
		return getBaseMapper().selectMapsIgnoreLogic(queryWrapper);
	}

	/**
	 * 查询所有列表（忽略逻辑删除条件）
	 *
	 * @see Wrappers#emptyWrapper()
	 */
	default List<Map<String, Object>> listMapsIgnoreLogic() {
		return listMapsIgnoreLogic(Wrappers.emptyWrapper());
	}

	/**
	 * 查询全部记录（忽略逻辑删除条件）
	 */
	default List<Object> listObjsIgnoreLogic() {
		return listObjsIgnoreLogic(Function.identity());
	}

	/**
	 * 查询全部记录（忽略逻辑删除条件）
	 *
	 * @param mapper 转换函数
	 */
	default <V> List<V> listObjsIgnoreLogic(Function<? super Object, V> mapper) {
		return listObjsIgnoreLogic(Wrappers.emptyWrapper(), mapper);
	}


	/**
	 * 根据 Wrapper 条件，查询全部记录（忽略逻辑删除条件）
	 *
	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
	 */
	default List<Object> listObjsIgnoreLogic(Wrapper<T> queryWrapper) {
		return listObjsIgnoreLogic(queryWrapper, Function.identity());
	}

	/**
	 * 根据 Wrapper 条件，查询全部记录（忽略逻辑删除条件）
	 *
	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
	 * @param mapper       转换函数
	 */
	default <V> List<V> listObjsIgnoreLogic(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
		return getBaseMapper().selectObjsIgnoreLogic(queryWrapper).stream().filter(Objects::nonNull).map(mapper)
				.collect(Collectors.toList());
	}

	/**
	 * 翻页查询 （忽略逻辑删除条件）
	 *
	 * @param page         翻页对象
	 * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
	 */
	default <E extends IPage<Map<String, Object>>> E pageMapsIgnoreLogic(E page, Wrapper<T> queryWrapper) {
		return getBaseMapper().selectMapsPageIgnoreLogic(page, queryWrapper);
	}

	/**
	 * 无条件翻页查询（忽略逻辑删除条件）
	 *
	 * @param page 翻页对象
	 * @see Wrappers#emptyWrapper()
	 */
	default <E extends IPage<Map<String, Object>>> E pageMapsIgnoreLogic(E page) {
		return pageMapsIgnoreLogic(page, Wrappers.emptyWrapper());
	}

	/**
	 * 获取对应 entity 的 BaseMapper
	 *
	 * @return BaseMapper
	 */
	@Override
	GiimallMapper<T> getBaseMapper();

}

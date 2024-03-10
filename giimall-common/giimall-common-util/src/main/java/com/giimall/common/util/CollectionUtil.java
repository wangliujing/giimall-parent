package com.giimall.common.util;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合工具类
 *
 * @author wangLiuJing
 * Created on 2020/4/30
 */
public class CollectionUtil {


	/**
	 * 校验集合是否为空
	 *
	 * @param coll 入参
	 * @return boolean
	 */
	public static boolean isEmpty(Collection coll) {
		return (coll == null || coll.isEmpty());
	}

	/**
	 * 校验集合是否不为空
	 *
	 * @param coll 入参
	 * @return boolean
	 */
	public static boolean isNotEmpty(Collection coll) {
		return !isEmpty(coll);
	}

	/**
	 * 判断Map是否为空
	 *
	 * @param map 入参
	 * @return boolean
	 */
	public static boolean isEmpty(Map map) {
		return (map == null || map.isEmpty());
	}

	/**
	 * 判断Map是否不为空
	 *
	 * @param map 入参
	 * @return boolean
	 */
	public static boolean isNotEmpty(Map map) {
		return !isEmpty(map);
	}

	/**
	 * 获取目标集合属性的值，重新组成集合
	 * @author wangLiuJing
	 * Created on 2020/4/27
	 *
	 * @param collection of type Collection<Map<String, T>>
	 * @param fieldName of type String
	 * @return List<T>
	 */
	@SneakyThrows
	public static <T> List<String> getMapValueToStringList(Collection<Map<String, T>> collection, String fieldName){
		List<String> resultList = new ArrayList<>();
		for (Map<String, T> e : collection) {
			resultList.add(String.valueOf(e.get(fieldName)));
		}
		return resultList;
	}


	/**
	 * 获取目标集合属性的值，重新组成集合
	 * @author wangLiuJing
	 * Created on 2020/4/27
	 *
	 * @param collection of type Collection<Map<String, T>>
	 * @param fieldName of type String
	 * @return List<T>
	 */
	@SneakyThrows
	public static <T> List<T> getMapValueToList(Collection<Map<String, T>> collection, String fieldName){
		List<T> resultList = new ArrayList<>();
		for (Map<String, T> e : collection) {
			resultList.add(e.get(fieldName));
		}
		return resultList;
	}

	/**
	 * 获取目标集合属性的值，重新组成集合
	 * @author wangLiuJing
	 * Created on 2020/4/27
	 *
	 * @param collection of type Collection<E>
	 * @param fieldName of type String
	 * @param returnType of type Class<T>
	 * @return List<T>
	 */
	@SneakyThrows
	public static <T,E> List<T> getFieldValueToList(Collection<E> collection, String fieldName, Class<T> returnType){
		List<T> resultList = new ArrayList<>();
		for (E e : collection) {
			Field field = BeanUtil.getDeclaredField(e.getClass(), fieldName);
			field.setAccessible(true);
			resultList.add((T) field.get(e));
		}
		return resultList;
	}


	/**
	 * 获取集合对象属性重新组成集合
	 * @author wangLiuJing
	 * Created on 2020/9/9
	 *
	 * @param collection of type Collection<E>
	 * @param fieldName of type String
	 * @param returnType of type Class<T>
	 * @return Set<T>
	 */
	@SneakyThrows
	public static <E,T> Set<T> getFieldValueToSet(Collection<E> collection, String fieldName, Class<T> returnType) {
		Set<T> set = new HashSet<>(collection.size());
		for (E e : collection) {
			Field declaredField = BeanUtil.getDeclaredField(e.getClass(), fieldName);
			declaredField.setAccessible(true);
			set.add((T) declaredField.get(e));
		}
		return set;
	}

	/**
	 * 获取集合对象属性重新组成一个有顺序的集合
	 * @author zhanghao
	 * Created on 2020/10/20
	 *
	 * @param collection of type Collection<E>
	 * @param fieldName of type String
	 * @param returnType of type Class<T>
	 * @return Set<T>
	 */
	@SneakyThrows
	public static <E,T> Set<T> getFieldValueToSetSequential(Collection<E> collection, String fieldName,
															Class<T> returnType) {
		Set<T> set = new LinkedHashSet<>(collection.size());
		for (E e : collection) {
			Field declaredField = BeanUtil.getDeclaredField(e.getClass(), fieldName);
			declaredField.setAccessible(true);
			set.add((T) declaredField.get(e));
		}
		return set;
	}

	/**
	 * 筛选出集合里面 对象属性fieldName的值 等于 fieldValue
	 * 注意fieldValue 是否需要重写equals方法
	 * @author wangLiuJing
	 * Created on 2020/4/30
	 *
	 * @param collection of type Collection<E>
	 * @param fieldName of type String
	 * @param fieldValue of type String
	 * @return List<E>
	 */
	@SneakyThrows
	public static <E> List<E> getListByFieldValue(Collection<E> collection, String fieldName, Object fieldValue){
		List<E> resultList = new ArrayList<>();
		for (E e : collection) {
			Field field = BeanUtil.getDeclaredField(e.getClass(), fieldName);
			field.setAccessible(true);
			if(fieldValue == null && field.get(e) == null){
				resultList.add(e);
			} else {
				if(fieldValue.equals(field.get(e))){
					resultList.add(e);
				}
			}
		}
		return resultList;
	}

	/**
	 * 筛选出集合里面 对象属性fieldName的值不为空的集合
	 * 注意fieldValue 是否需要重写equals方法
	 * @author wangLiuJing
	 * Created on 2020/4/30
	 *
	 * @param collection of type Collection<E>
	 * @param fieldName of type String
	 * @return List<E>
	 */
	@SneakyThrows
	public static <E> List<E> getListByFieldIsNotNull(Collection<E> collection, String fieldName){
		List<E> resultList = new ArrayList<>();
		for (E e : collection) {
			Field field = BeanUtil.getDeclaredField(e.getClass(), fieldName);
			field.setAccessible(true);
			if(field.get(e) != null){
				resultList.add(e);
			}
		}
		return resultList;
	}

	/**
	 * 根据fileName获取目标集合中的一个元素
	 * @author zhanghao
	 * Created on 2020/5/13
	 *
	 * @param collection of type Collection<E>
	 * @param fieldName of type String
	 * @param fieldValue of type Object
	 * @return E
	 */
	@SneakyThrows
	public static <E> E getOneByFieldValue(Collection<E> collection, String fieldName, Object fieldValue){
		for (E e : collection) {
			Field field = BeanUtil.getDeclaredField(e.getClass(), fieldName);
			field.setAccessible(true);
			if(fieldValue == null && field.get(e) == null){
				return e;
			} else {
				if(fieldValue.equals(field.get(e))){
					return e;
				}
			}
		}
		return null;
	}

	/**
	 * 创建集合
	 * @author wangLiuJing
	 * Created on 2021/4/25
	 *
	 * @param returnElementType of Class
	 * @param elements of type Object...
	 * @return List<T>
	 */
	public static <T> List<T> newArrayList(Class<T> returnElementType, Object... elements) {
		return  (List<T>)new ArrayList(Arrays.asList(elements));
	}

	/**
	 * 创建集合
	 * @author wangLiuJing
	 * Created on 2021/4/25
	 *
	 * @param returnElementType of Class
	 * @param elements of type Object...
	 * @return Set<T>
	 */
	public static <T> Set<T> newHashSet(Class<T> returnElementType, Object... elements) {
		return  (Set<T>)new HashSet(Arrays.asList(elements));
	}

	/**
	 * 合并集合为List
	 * @author wangLiuJing
	 * Created on 2021/11/3
	 *
	 * @param list of type Collection<T>...
	 * @return List<T>
	 */
	public static <T> List<T> mergeToList(Collection<T>... list){
		return Stream.of(list).flatMap(Collection::stream).collect(Collectors.toList());
	}

	/**
	 * 合并集合为Set
	 * @author wangLiuJing
	 * Created on 2021/11/3
	 *
	 * @param list of type Collection<T>...
	 * @return Set<T>
	 */
	@SafeVarargs
	public static <T> Set<T> mergeToSet(Collection<T>... list){
		return Stream.of(list).flatMap(Collection::stream).collect(Collectors.toSet());
	}
}

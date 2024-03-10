package com.giimall.common.util;


import com.giimall.common.constant.SymbolConstant;
import com.giimall.common.exception.CommonException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 对象和map互转工具类
 *
 * @author wangLiuJing
 * Created on 2019/9/24
 */
@Slf4j
public class BeanMapUtil {

    /**
     * 将对象属性转化为map结合
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>(30);
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map中的数据转化为指定对象的同名属性中
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        try {
            T bean = clazz.newInstance();
            BeanUtils.populate(bean, map);
            return bean;
        } catch (Exception e) {
            log.error("map转换为对象失败", e);
            throw new RuntimeException("map转换为对象失败", e);
        }
    }

    /**
     * 将map集合中的数据转化为指定对象的同名属性中
     *
     * @param listMap of type List<Map<String, Object>>
     * @param clazz   of type Class
     * @return List
     * @author wangLiuJing
     * Created on 2019/9/29
     */
    public static <T> List<T> listMapToBean(List<Map<String, Object>> listMap, Class<T> clazz) {
        if (listMap == null || listMap.isEmpty()) {
            return null;
        }
        List<T> listBean = new ArrayList<>(listMap.size());

        for (Map<String, Object> stringObjectMap : listMap) {
            listBean.add(mapToBean(stringObjectMap, clazz));
        }
        return listBean;
    }


    /**
     * 将List转化为Map，Map的key是指定属性fieldName的值，这样方便通过值获取对象
     *
     * @param listMap   of type List listMap
     * @param fieldName of type String
     * @param keyType of type Class
     * @return List
     * @author wangLiuJing
     * Created on 2019/9/29
     */
    @SneakyThrows
    public static <T, E> Map<E, T> listToMapByField(List<T> listMap, String fieldName, Class<E> keyType) {
        return listMap.stream().collect(Collectors.toMap((t) ->{
            // 通过反射根据字段名获取字段值
            Field declaredField = BeanUtil.getDeclaredField(t.getClass(), fieldName);
            declaredField.setAccessible(true);
            E key = null;
            try {
                key = (E) declaredField.get(t);
            } catch (Exception ex){
                throw new CommonException(String.format("属性【%s】获取失败", fieldName));
            }

            return key;
        }, Function.identity(), (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); }));
    }

    /**
     * 将List转化为Map，Map的key是指定属性fieldName的值所拼接（拼接的符号为'_'）
     *
     * @param listMap   of type List listMap
     * @param fieldNames of type String[]
     * @return List
     * @author wangLiuJing
     * Created on 2019/9/29
     */
    @SneakyThrows
    public static <T> Map<String, T> listToMapByFields(List<T> listMap, String... fieldNames) {
        return listMap.stream().collect(Collectors.toMap((t) ->{
            // 通过反射根据字段名获取字段值拼接
            List<CharSequence> fieldNameValue = new ArrayList<>();
            for (String fieldName : fieldNames) {
                Field declaredField = BeanUtil.getDeclaredField(t.getClass(), fieldName);
                declaredField.setAccessible(true);
                try {
                    fieldNameValue.add(declaredField.get(t).toString());
                } catch (Exception ex){
                    throw new CommonException(String.format("属性【%s】获取失败", fieldName));
                }
            }
            // 拼接传入字段
            return StringUtil.join(fieldNameValue, SymbolConstant.UNDERLINE);
        }, Function.identity(), (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); }));
    }


    /**
     * 根据fieldName分组查询集合列表
     *
     * @param listMap   of type List listMap
     * @param fieldName of type String[]
     * @param keyType of type Class
     * @return List
     * @author wangLiuJing
     * Created on 2019/9/29
     */
    @SneakyThrows
    public static <E, T> Map<E, List<T>> listToMapListByField(List<T> listMap, String fieldName, Class<E> keyType) {
        return listMap.stream().collect(Collectors.groupingBy((t) ->{
            Field declaredField = BeanUtil.getDeclaredField(t.getClass(), fieldName);
            declaredField.setAccessible(true);
            try {
                return (E) declaredField.get(t);
            } catch (Exception ex){
                throw new CommonException(String.format("属性【%s】获取失败", fieldName));
            }
        }));
    }

}

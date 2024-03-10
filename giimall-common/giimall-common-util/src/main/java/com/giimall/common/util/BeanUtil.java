package com.giimall.common.util;


import com.giimall.common.exception.CommonException;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 王柳敬
 * @version 创建时间：2018年10月18日---下午3:42:45
 * @description
 */
public class BeanUtil extends BeanUtils {

    /**
     *
     * @author wangLiuJing
     * Created on 2021/12/28
     *
     * @param source of type Object 源对象
     * @param target of type Object 目标对象
     * @param requireFields of type 需要复制的属性
     * @throws BeansException when
     */
    @Deprecated
    public static void copyProperties(Object source, Object target, String... requireFields) {
        for (String fieldName : requireFields) {
            Method readMethod = BeanUtil.getReadMethod(source.getClass(), fieldName);
            Method writeMethod = BeanUtil.getWriteMethod(target.getClass(), fieldName);
            try {
                writeMethod.invoke(target, readMethod.invoke(source));
            } catch (Exception e) {
                throw new CommonException(e);
            }
        }
    }
    /**
     * 判断对象属性是否全部为null，excludeAttrName不包括排除属性
     * 如果targetSuperObject不为空该方法就是判断所有父熟悉，不包括排除属性
     *
     * @param currentObject   当前对象
     * @param targetClass     目标class对象，用来获取对象属性的get方法，这个地方只能是currentObject的父class,或者是currentObject的class对象
     * @param excludeAttrName 排除属性名
     * @return
     * @throws IntrospectionException
     */
    public static boolean isAllNullAttrValue(Object currentObject, Class<?> targetClass, String... excludeAttrName)
            throws Exception {

        BeanInfo beanInfo = Introspector.getBeanInfo(targetClass);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        List<String> asList = Arrays.asList(excludeAttrName);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Method readMethod = propertyDescriptor.getReadMethod();

            if (!asList.contains(propertyDescriptor.getName()) && !"getClass".equals(readMethod.getName())) {
                if (readMethod.invoke(currentObject) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 把父显示属性全部赋予子
     *
     * @param superObj
     * @param targetClass
     * @return
     * @throws Exception
     */
    public static <E extends T, T> E getChildObj(T superObj, Class<E> targetClass) throws Exception {
        E newInstance = targetClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(superObj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Method readMethod = propertyDescriptor.getReadMethod();
            if (propertyDescriptor.getWriteMethod() != null) {
                propertyDescriptor.getWriteMethod().invoke(newInstance, readMethod.invoke(superObj));
            }
        }
        return newInstance;
    }

    /**
     * 把对象属性赋予目标对象(对象属性必须相同才会设置值)
     *
     * @param
     * @param targetClass
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static <T> T getTargetObj(Object currentObj, Class<T> targetClass) throws Exception {

        T targetInstance = targetClass.newInstance();

        PropertyDescriptor[] targetPropertyDescriptors = Introspector.getBeanInfo(targetClass).getPropertyDescriptors();
        PropertyDescriptor[] currentPropertyDescriptors = Introspector.getBeanInfo(currentObj.getClass()).getPropertyDescriptors();

        for (PropertyDescriptor targetPropertyDescriptor : targetPropertyDescriptors) {

            Method targetWriteMethod = targetPropertyDescriptor.getWriteMethod();
            if (targetWriteMethod != null) {

                String targeName = targetWriteMethod.getName();
                for (PropertyDescriptor currentPropertyDescriptor : currentPropertyDescriptors) {

                    Method currentWriteMethod = currentPropertyDescriptor.getWriteMethod();

                    if (currentWriteMethod != null && currentWriteMethod.getName().equals(targeName)) {
                        Method readMethod = currentPropertyDescriptor.getReadMethod();
                        targetWriteMethod.invoke(targetInstance, readMethod.invoke(currentObj));
                    }
                }
            }

        }

        return targetInstance;
    }


    /**
     * 判断 待比较对象 相对于 快照对象 的属性是否有改变
     *
     * @param toCompare 待比较对象
     * @param snap 快照（对比基准值）
     * @param ignoreNullValue 是否忽略待比较对象的空值（如果待比较对象的属性值为空则该属性不进行比较）
     * @param excludeAttrName 忽略属性的属性名称（设置某些属性不进行比较）
     * @return boolean
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @SneakyThrows
    public static <T> boolean isSameValue(T toCompare, T snap,  boolean ignoreNullValue, String... excludeAttrName) {

        if(toCompare == null) {
            if(ignoreNullValue) {
                return true;
            } else {
                return snap == null;
            }
        }
        if(snap == null) {
            return toCompare == null;
        }

        if(isBaseType(toCompare) || toCompare instanceof String) {
            return toCompare.equals(snap);
        }

        if(toCompare instanceof Collection) {
            Collection toCompareCollection = (Collection) toCompare;
            Collection snapCollection = (Collection) snap;
            if(toCompareCollection.size() != snapCollection.size()) {
                return false;
            }

            Iterator toCompareIterator = toCompareCollection.iterator();
            Iterator snapIterator = snapCollection.iterator();
            while (toCompareIterator.hasNext() && snapIterator.hasNext()) {
                if(!isSameValue(toCompareIterator.next(), snapIterator.next(),  ignoreNullValue)) {
                    return false;
                }
            }
            return true;
        }
        if(toCompare.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(toCompare); i++) {
                if(!isSameValue(Array.get(toCompare, i), Array.get(snap, i),  ignoreNullValue)) {
                    return false;
                }
            }
            return true;
        }
        Set<String> excludeAttrNameSet = null;
        if(excludeAttrName != null) {
            excludeAttrNameSet = CollectionUtil.newHashSet(String.class, excludeAttrName);
        }
        PropertyDescriptor[] targetPropertyDescriptors = Introspector.getBeanInfo(snap.getClass()).getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : targetPropertyDescriptors) {
            boolean isverify = excludeAttrNameSet == null || !excludeAttrNameSet.contains(propertyDescriptor.getName());
            if (isverify) {
                Method readMethod = propertyDescriptor.getReadMethod();
                if("getClass".equals(readMethod.getName())){
                    continue;
                }
                Object compareValue = readMethod.invoke(toCompare);
                Object snapValue = readMethod.invoke(snap);
                if(!isSameValue(compareValue, snapValue,  ignoreNullValue)) {
                    return false;
                }

            }
        }
        return true;
    }

    /**
     * 是否是基本类型
     * @param obj
     * @return boolean
     */
    public static boolean isBaseType(Object obj) {
        Class clazz = obj.getClass();
        if (clazz == Integer.class || clazz ==Integer.TYPE
                || clazz == Byte.class || clazz == Byte.TYPE
                || clazz == Short.class || clazz == Short.TYPE
                || clazz == Character.class || clazz == Character.TYPE
                || clazz == Long.class || clazz == Long.TYPE
                || clazz == Double.class || clazz == Double.TYPE
                || clazz == Float.class || clazz == Float.TYPE
                || clazz == Boolean.class || clazz == Boolean.TYPE
        ){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 对象序列化到字节数组
     *
     * @param object of type Object
     * @return byte[]
     * @throws IOException when
     * @author wangLiuJing
     * Created on 2019/9/18
     */
    public static byte[] objectToBytes(Object object) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("对象序列化异常", e);
        }
    }

    /**
     * 字节数组反序列化到对象
     *
     * @param bytes of type byte[]
     * @return Object
     * @author wangLiuJing
     * Created on 2019/9/18
     */
    public static <T> T bytesToObject(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (T) objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException("对象反序列化异常", e);
        }
    }

    /**
     * 根据属性名获取属性对象
     *
     * @param clazz     of type Class
     * @param fieldName of type String
     * @return Field
     * @author wangLiuJing
     * Created on 2020/4/17
     */
    public static Field getDeclaredField(Class clazz, String fieldName) {
        if (clazz == null) {
            throw new CommonException("clazz参数不能为空");
        }
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            return getDeclaredField(clazz.getSuperclass(), fieldName);
        }
    }

    /**
     * 内省机制根据属性名获取对应的get方法
     *
     * @param clazz     of type Class
     * @param fieldName of type String
     * @return Field
     * @author wangLiuJing
     * Created on 2020/4/17
     */
    public static Method getReadMethod(Class clazz, String fieldName) {
        if (clazz == null) {
            throw new CommonException("clazz参数不能为空");
        }
        try {
            //使用内省，通过传入的类信息, 得到这个Bean类的封装对象BeanInfo
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            //获取bean类的 get/set方法 数组
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if(pd.getName().equals(fieldName)){
                    return pd.getReadMethod();
                }
            }
            throw new NoSuchMethodException(fieldName);
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }


    /**
     * 内省机制根据属性名获取对应的set方法
     *
     * @param clazz     of type Class
     * @param fieldName of type String
     * @return Field
     * @author wangLiuJing
     * Created on 2020/4/17
     */
    public static Method getWriteMethod(Class clazz, String fieldName) {
        if (clazz == null) {
            throw new CommonException("clazz参数不能为空");
        }
        try {
            //使用内省，通过传入的类信息, 得到这个Bean类的封装对象BeanInfo
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            //获取bean类的 get/set方法 数组
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if(pd.getName().equals(fieldName)){
                    return pd.getWriteMethod();
                }
            }
            throw new NoSuchMethodException(fieldName);
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    /**
     * 获取类的所有属性包括父类的属性
     *
     * @param clazz of type Class
     * @return List<Field>
     * @author wangLiuJing
     * Created on 2020/4/17
     */
    public static Field[] getDeclaredField(Class clazz) {
        List<Field> declaredField = getDeclaredField(clazz, new ArrayList<>());
        return declaredField.toArray(new Field[declaredField.size()]);
    }


    private static List<Field> getDeclaredField(Class clazz, List<Field> list) {
        Field[] declaredFields = clazz.getDeclaredFields();
        list.addAll(Arrays.asList(declaredFields));
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            getDeclaredField(superclass, list);
        }
        return list;
    }

}


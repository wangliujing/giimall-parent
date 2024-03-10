package com.giimall.common.util;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * protostuff序列化工具类
 *
 * @author wangLiuJing
 * @date 2022/03/16
 */
@Slf4j
public class ProtostuffUtil {

    /**
     * 序列化对象
     *
     * @param obj
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            return null;
        }

        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
        // 分配1kb内存
        LinkedBuffer buffer = LinkedBuffer.allocate(1024);
        byte[] protoStuff;
        try {
            protoStuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            log.error("Failed to serializer, obj:{}", obj, e);
            throw new RuntimeException("Failed to serializer");
        } finally {
            buffer.clear();
        }
        return protoStuff;
    }

    /**
     * 反序列化对象
     *
     * @param paramArrayOfByte
     * @param targetClass
     * @return
     */
    public static <T> T deserialize(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            return null;
        }

        T instance;
        try {
            instance = targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("Failed to deserialize", e);
            throw new RuntimeException("Failed to deserialize");
        }

        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        ProtostuffIOUtil.mergeFrom(paramArrayOfByte, instance, schema);
        return instance;
    }


    /**
     * 序列化列表
     *
     * @param objList
     * @return
     */
    public static <T> byte[] serializeList(List<T> objList) {
        if (CollectionUtil.isEmpty(objList)) {
            return null;
        }

        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(objList.get(0).getClass());
        // 分配1kb内存
        LinkedBuffer buffer = LinkedBuffer.allocate(1024);
        byte[] protoStuff;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(bos, objList, schema, buffer);
            protoStuff = bos.toByteArray();
        } catch (Exception e) {
            log.error("Failed to serializer, obj list:{}", objList, e);
            throw new RuntimeException("Failed to serializer");
        } finally {
            buffer.clear();
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return protoStuff;
    }

    /**
     * 反序列化列表
     *
     * @param paramArrayOfByte
     * @param targetClass
     * @return
     */
    public static <T> List<T> deserializeList(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            return null;
        }

        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        List<T> result;
        try {
            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(paramArrayOfByte), schema);
        } catch (IOException e) {
            log.error("Failed to deserialize", e);
            throw new RuntimeException("Failed to deserialize");
        }
        return result;
    }
}

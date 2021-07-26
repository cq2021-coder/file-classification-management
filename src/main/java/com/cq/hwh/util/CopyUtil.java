package com.cq.hwh.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 复制工具
 *
 * @author 程崎
 * @date 2021/06/11
 */
public class CopyUtil {

    /**
     * 复制
     * 单体复制
     *
     * @param source 源
     * @param clazz  clazz
     * @return {@link T}
     */
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(source, obj);
        return obj;
    }

    /**
     * 复制清单
     * 列表复制
     *
     * @param source 源
     * @param clazz  clazz
     * @return {@link List<T>}
     */
    public static <T> List<T> copyList(List source, Class<T> clazz) {
        List<T> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)){
            for (Object c: source) {
                T obj = copy(c, clazz);
                target.add(obj);
            }
        }
        return target;
    }
}

package com.jsj.orm.map;

import lombok.NonNull;

import java.util.Map;

/**
 * 用于基本类型的包装类，String以及Date类等JDBC支持的类型
 *
 * @param <T>
 * @author jiangshenjie
 */
public class BasicResultMapHandler<T> implements ResultMapHandler<T> {
    /**
     * 映射对象类型
     */
    private Class<T> clz;

    public BasicResultMapHandler(@NonNull Class<T> clz) {
        this.clz = clz;
    }

    @Override
    public T mapper(Map<String, Object> results) {
        Object value = null;
        for (Map.Entry<String, Object> entry : results.entrySet()) {
            value = entry.getValue();
            break;
        }
        return clz.cast(value);
    }
}

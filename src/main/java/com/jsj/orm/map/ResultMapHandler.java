package com.jsj.orm.map;

import com.jsj.orm.exception.ResultCastException;

import java.util.Map;

/**
 * 数据表字段-> DO的映射
 *
 * @param <T>
 * @author jiangshenjie
 */
public interface ResultMapHandler<T> {
    /**
     * 映射处理
     *
     * @param results sql查询结果：key为数据表的字段名，value为字段值
     * @return 返回的映射对象
     * @throws ResultCastException
     */
    T mapper(Map<String, Object> results) throws ResultCastException;
}

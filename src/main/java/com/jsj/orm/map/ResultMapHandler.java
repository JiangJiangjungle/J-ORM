package com.jsj.orm.map;

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
     * @param results 封装类sql响应结果的map：key为数据表的字段名，value为字段值
     * @return 返回的映射对象
     */
    T mapper(Map<String, Object> results);
}

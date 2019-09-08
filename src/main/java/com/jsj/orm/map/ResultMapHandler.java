package com.jsj.orm.map;

import java.util.Map;

/**
 * 数据表字段-> PO的映射
 *
 * @param <T>
 * @author jiangshenjie
 */
public interface ResultMapHandler<T> {
    /**
     * 映射处理 key为数据表的字段名，value为字段值
     *
     * @param results
     * @return 返回的映射对象
     */
    T mapper(Map<String, Object> results);
}

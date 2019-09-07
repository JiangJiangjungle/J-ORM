package com.jsj.orm.mapper;

import java.util.Map;

/**
 * database数据-> TO的映射
 *
 * @param <E>
 * @author jiangshenjie
 */
public interface ResultMapper<E> {
    /**
     * 映射处理 key为数据表的字段名，value为字段值
     *
     * @param results
     * @return 返回的映射对象
     */
    E mapper(Map<String, Object> results);
}

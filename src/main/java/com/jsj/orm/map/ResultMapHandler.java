package com.jsj.orm.map;

import java.util.Map;

/**
 * database数据-> PO的映射
 *
 * @param <E>
 * @author jiangshenjie
 */
public interface ResultMapHandler<E> {
    /**
     * 映射处理 key为数据表的字段名，value为字段值
     *
     * @param results
     * @return 返回的映射对象
     */
    E mapper(Map<String, Object> results);
}

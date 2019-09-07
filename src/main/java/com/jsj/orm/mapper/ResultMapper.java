package com.jsj.orm.mapper;

import java.util.Map;

/**
 * 数据库字段到TO的映射
 *
 * @param <E>
 * @author jiangshenjie
 */
public interface ResultMapper<E> {
    E mapper(Map<String, Object> results);
}

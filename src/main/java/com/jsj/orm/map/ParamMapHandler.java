package com.jsj.orm.map;

/**
 * PO->sql参数数组的映射
 *
 * @param <T>
 * @author jiangshenjie
 */
public interface ParamMapHandler<T> {
    /**
     * 映射成参数数组
     *
     * @param e
     * @return
     */
    Object[] mapper(T e);
}

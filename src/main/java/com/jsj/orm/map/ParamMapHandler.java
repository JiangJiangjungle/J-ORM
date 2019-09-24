package com.jsj.orm.map;

/**
 * DO->sql参数数组的映射
 *
 * @param <T>
 * @author jiangshenjie
 */
public interface ParamMapHandler<T> {
    /**
     * 映射成参数数组
     *
     * @param e 待映射的对象
     * @return 对应到sql中的参数数组，按顺序排列
     */
    Object[] mapper(T e);
}

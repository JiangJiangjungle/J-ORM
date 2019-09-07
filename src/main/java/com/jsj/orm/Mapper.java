package com.jsj.orm;

import com.jsj.orm.map.ResultMapHandler;

import java.util.List;

/**
 * @author jiangshenjie
 */
public interface Mapper {

    /**
     * select one result
     *
     * @param sql
     * @param resultMapHandler
     * @param params
     * @param <E>
     * @return one result or ull
     */
    <E> E selectOne(String sql, ResultMapHandler<E> resultMapHandler, Object... params);

    /**
     * select multi result
     *
     * @param sql
     * @param resultMapHandler
     * @param params
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String sql, ResultMapHandler<E> resultMapHandler, Object... params);

    /**
     * update
     *
     * @param sql
     * @param params
     * @return if succeed
     */
    boolean update(String sql, Object... params);

    /**
     * transaction commit
     */
    void commit();

    /**
     * transaction rollback
     */
    void rollback();
}

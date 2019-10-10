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
     * @param <T>
     * @return one result or ull
     */
    <T> T selectOne(String sql, ResultMapHandler<T> resultMapHandler, Object... params);

    /**
     * select multi result
     *
     * @param sql
     * @param resultMapHandler
     * @param params
     * @param <T>
     * @return
     */
    <T> List<T> selectList(String sql, ResultMapHandler<T> resultMapHandler, Object... params);

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

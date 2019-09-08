package com.jsj.orm.executor;

import com.jsj.orm.config.Configuration;
import com.jsj.orm.map.ResultMapHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author jiangshenjie
 */
public interface Executor {
    /**
     * query
     *
     * @param sql
     * @param resultMapHandler
     * @param params
     * @param <E>
     * @return
     * @throws SQLException
     */
    <E> List<E> query(String sql, ResultMapHandler<E> resultMapHandler, Object... params) throws SQLException;

    /**
     * update
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    boolean update(String sql, Object... params) throws SQLException;

    /**
     * 事务提交
     *
     * @throws SQLException
     */
    void commit() throws SQLException;

    /**
     * 事务回滚
     *
     * @throws SQLException
     */
    void rollback() throws SQLException;

    Configuration getConfiguration();
}

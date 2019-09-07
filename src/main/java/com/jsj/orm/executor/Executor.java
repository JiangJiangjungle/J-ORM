package com.jsj.orm.executor;

import com.jsj.orm.mapper.ResultMapper;

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
     * @param resultMapper
     * @param params
     * @param <E>
     * @return
     * @throws SQLException
     */
    <E> List<E> query(String sql, ResultMapper<E> resultMapper, Object... params) throws SQLException;

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
}

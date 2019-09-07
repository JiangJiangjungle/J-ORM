package com.jsj.orm.executor;

import com.jsj.orm.mapper.ResultMapper;

import java.sql.SQLException;
import java.util.List;

/**
 * @author jiangshenjie
 */
public interface Executor {
    <E> List<E> query(String sql, ResultMapper<E> resultMapper, Object... params) throws SQLException;

    boolean update(String sql, Object... params) throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;
}

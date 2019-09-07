package com.jsj.orm.executor;

import com.jsj.orm.mapper.ResultMapper;
import com.jsj.orm.transaction.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每次调用都会新建一个Executor，即Executor是一次性的
 *
 * @author jiangshenjie
 */
public class BaseExecutor implements Executor {
    private Transaction transaction;

    public BaseExecutor(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public <E> List<E> query(String sql, ResultMapper<E> resultMapper, Object... params) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = prepare(sql, params);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData data = resultSet.getMetaData();
            List<E> list = new ArrayList<>();
            for (int count = data.getColumnCount(); resultSet.next(); ) {
                Map<String, Object> values = new HashMap<>(count);
                for (int i = 1; i <= count; i++) {
                    values.put(data.getColumnName(i), resultSet.getObject(i));
                }
                list.add(resultMapper.mapper(values));
            }
            return list;
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public boolean update(String sql, Object... params) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = prepare(sql, params);
            return statement.execute();
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public void commit() throws SQLException {
        transaction.commit();
    }

    @Override
    public void rollback() throws SQLException {
        transaction.rollback();
    }

    protected PreparedStatement prepare(String sql, Object... params) throws SQLException {
        Connection connection = transaction.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        setParams(statement, params);
        return statement;
    }

    protected void setParams(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 1; i <= params.length; i++) {
            statement.setObject(i, params[i - 1]);
        }
    }

    protected void closeStatement(Statement statement) {
        if (statement == null) return;
        try {
            if (!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

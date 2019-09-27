package com.jsj.orm;

import com.jsj.orm.transaction.Transaction;
import com.jsj.orm.config.Configuration;
import com.jsj.orm.exception.TransactionClosedException;
import com.jsj.orm.map.ResultMapHandler;
import lombok.NonNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每个新事务都会新建一个Executor，即Executor是一次性的
 *
 * @author jiangshenjie
 */
public class BaseExecutor implements Executor {
    protected Configuration configuration;
    protected Transaction transaction;

    public BaseExecutor(@NonNull Configuration configuration, @NonNull Transaction transaction) {
        this.configuration = configuration;
        this.transaction = transaction;
    }

    @Override
    public <E> List<E> query(String sql, ResultMapHandler<E> resultMapHandler, Object... params) throws SQLException {
        checkTransactionIfClosed();
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
                //根据该行返回结果，映射为一个对象后添加到list
                E object = resultMapHandler.mapper(values);
                list.add(object);
            }
            return list;
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public boolean update(String sql, Object... params) throws SQLException {
        checkTransactionIfClosed();
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

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * 根据Connection和 sql， 获取PreparedStatement
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    protected PreparedStatement prepare(String sql, Object... params) throws SQLException {
        Connection connection = transaction.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        setParams(statement, params);
        return statement;
    }

    /**
     * 参数填充到PreparedStatement
     *
     * @param statement PreparedStatement
     * @param params    参数数组
     * @throws SQLException
     */
    protected void setParams(PreparedStatement statement, Object... params) throws SQLException {
        if (params == null) {
            return;
        }
        for (int i = 1; i <= params.length; i++) {
            statement.setObject(i, params[i - 1]);
        }
    }

    /**
     * 释放statement资源
     *
     * @param statement
     */
    protected void closeStatement(Statement statement) {
        if (statement == null) {
            return;
        }
        try {
            if (!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查事务是否已经关闭，事务关闭后不可再做任何操作
     *
     * @throws SQLException
     */
    protected void checkTransactionIfClosed() throws SQLException {
        if (transaction.isClosed()) {
            throw new TransactionClosedException("Transaction is closed!");
        }
    }
}

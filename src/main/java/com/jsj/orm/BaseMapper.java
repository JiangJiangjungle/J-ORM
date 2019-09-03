package com.jsj.orm;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T>
 * @author jiangshenjie
 */
public abstract class BaseMapper<T> {
    protected String tableName;

    public BaseMapper(String tableName) {
        this.tableName = tableName;
    }

    /**
     * table create sql
     */
    public abstract void createTableIfNotExists(Connection connection) throws SQLException;

    protected boolean execute(Connection connection, String sql, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        setParams(statement, params);
        return statement.execute();
    }

    protected T selectOne(Connection connection, String sql, Object... params) throws SQLException {
        List<T> results = selectList(connection, sql, params);
        return results.size() == 0 ? null : results.get(0);
    }

    protected List<T> selectList(Connection connection, String sql, Object... params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        setParams(statement, params);
        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData data = resultSet.getMetaData();
        List<T> list = new ArrayList<>();
        for (int count = data.getColumnCount(); resultSet.next(); ) {
            Map<String, Object> values = new HashMap<>(count);
            for (int i = 1; i <= count; i++) {
                values.put(data.getColumnName(i), resultSet.getObject(i));
            }
            list.add(mapper(values));
        }
        return list;
    }

    private void setParams(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 1; i <= params.length; i++) {
            statement.setObject(i, params[i - 1]);
        }
    }

    /**
     * 根据查询字段名和字段值封装成Java Object
     *
     * @param values
     * @return
     */
    protected abstract T mapper(Map<String, Object> values);
}

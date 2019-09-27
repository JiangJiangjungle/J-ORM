package com.jsj.orm;

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
     * @param sql              sql语句，参数由占位符?表示
     * @param resultMapHandler sql结果映射器
     * @param params           参数数组
     * @param <E>              封装的返回结果
     * @return
     * @throws SQLException
     */
    <E> List<E> query(String sql, ResultMapHandler<E> resultMapHandler, Object... params) throws SQLException;

    /**
     * update
     *
     * @param sql    sql语句，参数由占位符?表示
     * @param params 参数数组
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

    /**
     * 获取配置项
     *
     * @return
     */
    Configuration getConfiguration();
}

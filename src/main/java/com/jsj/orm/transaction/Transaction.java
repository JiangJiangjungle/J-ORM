package com.jsj.orm.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Copy from Mybatis
 *
 * @author jiangshenjie
 */
public interface Transaction {
    /**
     * Retrieve inner database connection
     *
     * @return DataBase connection
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

    /**
     * Commit inner database connection.
     *
     * @throws SQLException
     */
    void commit() throws SQLException;

    /**
     * Rollback inner database connection.
     *
     * @throws SQLException
     */
    void rollback() throws SQLException;

    /**
     * Close inner database connection.
     *
     * @throws SQLException
     */
    void close() throws SQLException;

    /**
     * Check connection if closed.
     * @return
     * @throws SQLException
     */
    boolean isClosed() throws SQLException;
}

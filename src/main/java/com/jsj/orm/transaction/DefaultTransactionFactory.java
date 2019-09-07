package com.jsj.orm.transaction;

import javax.sql.DataSource;

/**
 * @author jiangshenjie
 */
public class DefaultTransactionFactory {

    public Transaction newTransaction(DataSource ds, boolean autoCommit) {
        return new JdbcTransaction(ds, autoCommit);
    }
}

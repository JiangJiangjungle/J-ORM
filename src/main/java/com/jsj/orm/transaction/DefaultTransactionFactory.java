package com.jsj.orm.transaction;

import javax.sql.DataSource;

public class DefaultTransactionFactory {

    public Transaction newTransaction(DataSource ds, boolean autoCommit) {
        return new JdbcTransaction(ds, autoCommit);
    }
}

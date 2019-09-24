package com.jsj.orm.transaction;

import javax.sql.DataSource;

/**
 * @author jiangshenjie
 */
public class DefaultTransactionFactory implements TransactionFactory {

    @Override
    public Transaction newTransaction(DataSource ds, boolean autoCommit) {
        return new JdbcTransaction(ds, autoCommit);
    }
}

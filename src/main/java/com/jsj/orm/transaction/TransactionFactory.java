package com.jsj.orm.transaction;

import javax.sql.DataSource;

public interface TransactionFactory {
    Transaction newTransaction(DataSource ds, boolean autoCommit);
}

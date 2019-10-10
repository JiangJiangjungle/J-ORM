package com.jsj.orm.transaction;

import javax.sql.DataSource;

/**
 * @author jiangshenjie
 */
public interface TransactionFactory {
    /**
     * 创建一个事务对象
     *
     * @param ds
     * @param autoCommit
     * @return
     */
    Transaction newTransaction(DataSource ds, boolean autoCommit);
}

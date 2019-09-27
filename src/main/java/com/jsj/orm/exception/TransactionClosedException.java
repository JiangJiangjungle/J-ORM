package com.jsj.orm.exception;

import java.sql.SQLException;

/**
 * 当事务提交，回滚，关闭后再次进行操作，则抛出此异常
 *
 * @author jiangshenjie
 */
public class TransactionClosedException extends SQLException {
    public TransactionClosedException(String message) {
        super(message);
    }
}

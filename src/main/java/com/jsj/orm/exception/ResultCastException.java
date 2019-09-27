package com.jsj.orm.exception;

/**
 * 类型映射失败后抛出此异常
 *
 * @author jiangshenjie
 */
public class ResultCastException extends RuntimeException {
    public ResultCastException(String message) {
        super(message);
    }
}

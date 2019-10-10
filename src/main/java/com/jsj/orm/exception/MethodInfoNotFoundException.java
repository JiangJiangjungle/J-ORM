package com.jsj.orm.exception;

/**
 * 注册方法查找失败后调用此异常
 *
 * @author jiangshenjie
 */
public class MethodInfoNotFoundException extends RuntimeException {
    public MethodInfoNotFoundException() {
    }

    public MethodInfoNotFoundException(String message) {
        super(message);
    }
}

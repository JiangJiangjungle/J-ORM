package com.jsj.orm.exception;

/**
 * @author jiangshenjie
 */
public class MapperClosedException extends RuntimeException {
    public MapperClosedException(String message) {
        super(message);
    }
}

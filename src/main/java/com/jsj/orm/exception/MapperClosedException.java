package com.jsj.orm.exception;

public class MapperClosedException extends RuntimeException {
    public MapperClosedException(String message) {
        super(message);
    }
}

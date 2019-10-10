package com.jsj.orm.exception;

public class MethodInfoNotFoundException extends RuntimeException{
    public MethodInfoNotFoundException() {
    }

    public MethodInfoNotFoundException(String message) {
        super(message);
    }
}

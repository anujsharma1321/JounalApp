package com.firstProgram.demo.ExceptionHandler;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}

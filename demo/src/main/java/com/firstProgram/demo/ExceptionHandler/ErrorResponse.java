package com.firstProgram.demo.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
@Getter
@AllArgsConstructor
public class ErrorResponse {

    private Instant timestamp;
    private int status;
    private String errorCode;
    private String message;

    // constructors
    // getters
}
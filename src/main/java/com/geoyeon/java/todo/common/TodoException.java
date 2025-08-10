package com.geoyeon.java.todo.common;

import lombok.Getter;

@Getter
public class TodoException extends RuntimeException{
    private ErrorCode errorCode;

    public TodoException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

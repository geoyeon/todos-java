package com.geoyeon.java.todo.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private String code;
    private String message;
    private HttpStatus httpStatus;

    private ErrorResponse(HttpStatus httpStatus, String code, String message) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(HttpStatus httpStatus, String code, String message) {
        return new ErrorResponse(httpStatus, code, message );
    }
}

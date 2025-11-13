package com.schedule.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private Object details; // 타입 불일치 맞추기 위해 필드 타입 Object

    public ErrorResponse(ErrorMessage code, String message) {
        this.code = code.name();
        this.message = message;
        this.details = null;
    }

    public ErrorResponse(ErrorMessage errorMessage, Object details) {
        this.code = errorMessage.name();
        this.message = errorMessage.getMessage();
        this.details = details;
    }
}

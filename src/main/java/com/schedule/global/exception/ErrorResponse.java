package com.schedule.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private Map<String, String> details;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;

    }
}

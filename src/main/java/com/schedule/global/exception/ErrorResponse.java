package com.schedule.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 전역 예외 응답 DTO.
 * <p>발생한 예외 정보를 클라이언트에 전달하기 위한 응답 형식이다.</p>
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    // 상세 정보 (유효성 검사 등 추가 데이터)
    private Object details;

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

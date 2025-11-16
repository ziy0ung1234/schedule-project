package com.schedule.global.exception;

import lombok.Getter;
/**
 * 전역 커스텀 예외 클래스.
 * <p>서비스/도메인 계층에서 {@link ErrorMessage}를 통해 에러 상태를 표현한다.</p>
 */
@Getter
public class CustomException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

}

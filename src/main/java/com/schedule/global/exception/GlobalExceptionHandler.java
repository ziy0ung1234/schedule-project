package com.schedule.global.exception;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.*;

/**
 * 전역 예외 처리 클래스(Global Exception Handler)
 *
 * <p><b>설명:</b></p>
 * <p>애플리케이션 전역에서 발생하는 예외를 한 곳에서 처리
 * 각 예외 타입에 따라 공통 응답 형식({@link ErrorResponse})으로 반환</p>
 *
 * <p><b>예외 처리:</b></p>
 * <ul>
 *   <li><b>IllegalArgumentException</b> — 잘못된 입력값 (HTTP 400)</li>
 *   <li><b>IllegalArgumentException</b> — 인증 (HTTP 401)</li>
 *   <li><b>PropertyValueException</b> — JPA 엔티티의 not-null 속성 누락 (HTTP 400)</li>
 *   <li><b>HttpMessageNotReadableException</b> — 요청 Body가 비정상적이거나 누락 (HTTP 400)</li>
 *   <li><b>MethodArgumentNotValidException</b> — DTO 유효성 검증 실패 (HTTP 400)</li>
 * </ul>
 *
 */
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler extends RuntimeException{
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getErrorMessage().getStatus())
                .body(new ErrorResponse(e.getErrorMessage(), e.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<Map<String, String>> details = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> Map.of(
                        "field", fieldError.getField(),
                        "reason", fieldError.getDefaultMessage()
                ))
                .toList();
        return ResponseEntity
                .status(ErrorMessage.VALIDATION_ERROR.getStatus())
                .body(new ErrorResponse(ErrorMessage.VALIDATION_ERROR, details));
    }
}


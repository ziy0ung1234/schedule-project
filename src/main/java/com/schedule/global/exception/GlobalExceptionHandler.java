package com.schedule.global.exception;

import jakarta.persistence.*;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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
public class GlobalExceptionHandler {

    // IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        String message = e.getMessage();
        if (message.contains("로그인이 필요합니다")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("UNAUTHORIZED", message));
        }
        ErrorResponse response = new ErrorResponse("BAD_REQUEST", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    // PropertyValueException
    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ErrorResponse> handlePropertyValue(PropertyValueException e) {
        ErrorResponse response = new ErrorResponse("VALIDATION_ERROR",
                e.getPropertyName()+"값이 누락되었습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    //HttpMessageNotReadableException
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        ErrorResponse response = new ErrorResponse("VALIDATION_ERROR",
                "Request Body를 확인해 주세요.");
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    //MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ErrorResponse response = new ErrorResponse("VALIDATION_ERROR", "유효성 검사 실패", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse>handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorResponse response = new ErrorResponse("NOT_FOUND_ERROR",
                e.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    }


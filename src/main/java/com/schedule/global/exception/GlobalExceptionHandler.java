package com.schedule.global.exception;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.*;

/**
 * 전역 예외 처리 핸들러.
 * <p>컨트롤러 전반에서 발생한 예외를 잡아 표준 응답 형식으로 반환한다.</p>
 */
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
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


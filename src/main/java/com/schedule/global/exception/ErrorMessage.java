package com.schedule.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
/**
 * 전역 에러 메시지를 정의한 열거형 클래스
 * <p>각 예외 상황에 대한 HTTP 상태코드와 메시지를 함께 관리한다.</p>
 */
@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    //-----------------400-----------------------
    NOT_EXIST_BODY(HttpStatus.BAD_REQUEST,"Request Body를 확인해 주세요"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 실패"),
    EXIST_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 유저명입니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    NOT_MATCHED_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    //-----------------401-----------------------
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"로그인이 필요합니다."),
    //-----------------403-----------------------
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    //-----------------404-----------------------
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND,"해당 일정을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND,"해당 댓글을 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String message;

}

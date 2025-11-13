package com.schedule.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    //-----------------400-----------------------
    NOT_EXIST_BODY(HttpStatus.BAD_REQUEST,"Request Body를 확인해 주세요"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 실패"),
    EXIST_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 유저명입니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 유저명입니다."),
    //-----------------401-----------------------
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"로그인이 필요합니다."),
    //-----------------403-----------------------
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    //-----------------404-----------------------
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND,"해당 게시글을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND,"해당 댓글을 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String message;

}

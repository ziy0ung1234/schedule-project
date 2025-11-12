package com.schedule.comment.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 댓글 삭제 요청 DTO 클래스입니다.
 * <p>
 * 클라이언트가 댓글을 삭제할 때 필요한 요청 데이터를 정의합니다.
 * <br>
 * 댓글 삭제 시 비밀번호 검증이 필수입니다.
 * </p>
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>password</b> – 댓글 작성 시 설정된 비밀번호 (필수)</li>
 * </ul>
 *
 * <h2>유효성 제약 조건</h2>
 * <ul>
 *   <li>{@code @NotBlank}: 공백이나 null 불가</li>
 * </ul>
 */
@Getter
public class DeleteCommenteRequest {
    @NotBlank(message="비밀번호는 필수 값 입니다.")
    private String password;
}

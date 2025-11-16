package com.schedule.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 댓글 내용 수정 요청 DTO 클래스입니다.
 * <p>
 * 사용자가 기존 댓글의 <b>내용(content)</b>을 수정할 때 사용하는 요청 모델입니다.
 * <br>
 * 수정 요청 시 비밀번호 검증이 필수입니다.
 * </p>
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>content</b> – 변경할 댓글 내용 (필수 입력, 최대 200자)</li>
 *   <li><b>password</b> – 비밀번호 검증용 필드 (필수 입력)</li>
 * </ul>
 */
@Getter
@NoArgsConstructor
public class UpdateCommentRequest {
    @NotBlank(message = "변경된 내용이 없습니다.")
    @Size(max = 200, message = "댓글은 최대 200자까지 입력 가능합니다.")
    private String content;
    @NotBlank(message="비밀번호는 필수 값입니다.")
    private String password;
}

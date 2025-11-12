package com.schedule.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 댓글 수정 요청 DTO 클래스입니다.
 * <p>
 * 댓글 제목 또는 작성자명을 수정할 때 사용하는 요청 모델입니다.
 * <br>
 * 비밀번호 검증이 필수로 요구됩니다.
 * </p>
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>title</b> – 변경할 댓글 제목 (선택, 최대 30자)</li>
 *   <li><b>username</b> – 변경할 작성자 이름 (선택)</li>
 *   <li><b>password</b> – 비밀번호 검증용 필드 (필수)</li>
 * </ul>
 *
 * <h2>유효성 제약 조건</h2>
 * <ul>
 *   <li>{@code @Size(max=30)}: 제목은 최대 30자</li>
 *   <li>{@code @NotBlank}: 비밀번호는 필수 입력</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
public class UpdateCommentRequest {
    private String username;
    private String content;
    @NotBlank(message="비밀번호는 필수 값입니다.")
    private String password;
}

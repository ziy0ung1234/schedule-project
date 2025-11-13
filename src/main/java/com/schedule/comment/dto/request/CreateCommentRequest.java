package com.schedule.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 댓글 생성 요청 DTO 클래스입니다.
 * <p>
 * 클라이언트가 새로운 댓글을 생성할 때 전송하는 데이터 구조를 정의합니다.
 * <br>
 * 유효성 검사를 통해 필수 입력 항목(`title`, `description`)과 길이 제한을 검증합니다.
 * </p>
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>title</b> – 댓글 제목 (필수, 최대 30자)</li>
 *   <li><b>description</b> – 댓글 내용 (필수, 최대 200자)</li>
 *   <li><b>userId</b> – 작성자 ID (서버 측 세션에서 주입됨)</li>
 * </ul>
 *
 * <h2>유효성 제약 조건</h2>
 * <ul>
 *   <li>{@code @NotBlank}: 공백이나 null 불가</li>
 *   <li>{@code @Size(max=30)}: 제목은 최대 30자</li>
 *   <li>{@code @Size(max=200)}: 내용은 최대 200자</li>
 * </ul>
 */

@Getter
@NoArgsConstructor
public class CreateCommentRequest {
    @NotBlank(message="댓글 내용은 필수 값입니다.")
    @Size(max=200)
    private String content;
    private Long userId;
    private Long scheduleId;

}

package com.schedule.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 댓글 생성 요청 DTO 클래스입니다.
 * <p>
 * 클라이언트가 새로운 댓글을 작성할 때 서버로 전달하는 데이터 구조를 정의합니다.
 * <br>
 * 본 요청 객체는 댓글의 <b>내용</b>만 클라이언트에서 전달하며,
 * 작성자 ID({@code userId})와 일정 ID({@code scheduleId})는
 * 서버에서 세션 및 경로 변수로 주입됩니다.
 * </p>
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>content</b> – 댓글 본문 내용 (필수, 최대 200자)</li>
 *   <li><b>userId</b> – 작성자 ID (세션에서 주입됨)</li>
 *   <li><b>scheduleId</b> – 댓글이 속한 일정 ID (경로 변수에서 주입됨)</li>
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

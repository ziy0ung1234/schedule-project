package com.schedule.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 일정 수정 요청 DTO.
 * <p>제목·내용 수정 시 비밀번호 검증을 포함한다.</p>
 */
@Getter
@NoArgsConstructor
public class UpdateScheduleRequest {
    @Size(max=30)
    private String title;
    @Size(max=200)
    private String description;
    @NotBlank(message="비밀번호는 필수 값입니다.")
    private String password;
}

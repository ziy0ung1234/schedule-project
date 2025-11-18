package com.schedule.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 일정 생성 요청 DTO.
 * <p>제목과 내용을 포함하며, 작성자 ID는 세션에서 주입된다.</p>
 */
@Getter
@NoArgsConstructor
public class CreateScheduleRequest {
    @NotBlank(message="제목은 필수 값입니다.")
    @Size(max=30)
    private String title;
    @NotBlank(message="일정 내용은 필수 값입니다.")
    @Size(max=200)
    private String description;
}

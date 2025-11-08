package com.schedule.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UpdateScheduleRequest {
    @Size(max=30)
    private String title;
    private String username;
    @NotBlank(message="비밀번호는 필수 값입니다.")
    private String password;
}

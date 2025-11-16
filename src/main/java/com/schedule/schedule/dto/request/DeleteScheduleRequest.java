package com.schedule.schedule.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 일정 삭제 요청 DTO.
 * <p>삭제 시 비밀번호 검증에 사용된다.</p>
 */
@Getter
public class DeleteScheduleRequest {
    @NotBlank(message="비밀번호는 필수 값 입니다.")
    private String password;
}

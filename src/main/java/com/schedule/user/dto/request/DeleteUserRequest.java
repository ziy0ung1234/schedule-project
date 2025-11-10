package com.schedule.user.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteUserRequest {
    @NotBlank(message="비밀번호는 필수 값 입니다.")
    private String email;
    @NotBlank(message="비밀번호는 필수 값 입니다.")
    private String password;
}

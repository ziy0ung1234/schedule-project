package com.schedule.user.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
/**
 * 회원 탈퇴 요청 DTO.
 * <p>비밀번호 검증에 사용된다.</p>
 */
@Getter
public class DeleteUserRequest {
    @NotBlank(message="비밀번호는 필수 값 입니다.")
    private String password;
}

package com.schedule.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 사용자 정보 수정 요청 DTO.
 * <p>이름·이메일 수정 시 비밀번호 검증을 포함한다.</p>
 */
@Getter
@NoArgsConstructor
public class UpdateUserRequest {
    @Size(max=30)
    private String username;
    @Size(max=50)
    private String email;
    @NotBlank(message="비밀번호는 필수 값입니다.")
    private String password;
}

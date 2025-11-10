package com.schedule.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message="이름은 필수 값입니다.")
    @Size(max=30)
    private String username;
    @NotBlank(message="이메일은 필수 값입니다.")
    @Size(max=50)
    private String email;
    @NotBlank(message="비밀번호는 필수 값입니다.")
    private String password;

}

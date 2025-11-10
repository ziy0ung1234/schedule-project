package com.schedule.user.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message="이름은 필수 값입니다.")
    @Size(max=30)
    private String username;
    @Email(message="올바른 이메일 형식이 아닙니다.")
    @NotBlank(message="이메일은 필수 값입니다.")
    @Size(max=50)
    private String email;
    @NotBlank(message="비밀번호는 필수 값입니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,20}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함해 8~20자여야 합니다."
    )
    private String password;

}

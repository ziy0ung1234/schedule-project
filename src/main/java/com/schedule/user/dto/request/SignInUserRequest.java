package com.schedule.user.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * 사용자 로그인 요청 DTO 클래스입니다.
 * <p>
 * 클라이언트가 로그인할 때 전달하는 이메일과 비밀번호 정보를 정의합니다.
 * <br>
 * 모든 필드는 {@code javax.validation} 어노테이션을 통해 유효성 검증을 수행합니다.
 * </p>
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>email</b> – 사용자 이메일 (필수, 형식 검증, 최대 50자)</li>
 *   <li><b>password</b> – 사용자 비밀번호 (필수, 영문/숫자/특수문자 포함 8~20자)</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
public class SignInUserRequest {
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

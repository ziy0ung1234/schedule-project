package com.schedule.user.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * 사용자 회원가입 요청 DTO 클래스입니다.
 * <p>
 * 회원가입 시 클라이언트가 전달하는 사용자 정보(이름, 이메일, 비밀번호)를 정의합니다.
 * <br>
 * 모든 필드는 유효성 검증 어노테이션으로 검증되며, 잘못된 입력 시 {@code MethodArgumentNotValidException}이 발생합니다.
 * </p>
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>username</b> – 사용자 이름 (필수, 최대 30자)</li>
 *   <li><b>email</b> – 이메일 주소 (필수, 형식 및 최대 50자 제한)</li>
 *   <li><b>password</b> – 비밀번호 (필수, 영문/숫자/특수문자 포함 8~20자)</li>
 * </ul>
 */
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

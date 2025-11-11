package com.schedule.user.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
/**
 * 사용자 삭제 요청 DTO 클래스입니다.
 * <p>
 * 사용자가 계정을 삭제할 때 필요한 인증 정보를 담습니다.
 * 이메일과 비밀번호가 모두 일치해야 삭제가 수행됩니다.
 * </p>
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>email</b> – 사용자 이메일 (필수)</li>
 *   <li><b>password</b> – 사용자 비밀번호 (필수)</li>
 * </ul>
 */
@Getter
public class DeleteUserRequest {
    @NotBlank(message="이메일은 필수 값 입니다.")
    private String email;
    @NotBlank(message="비밀번호는 필수 값 입니다.")
    private String password;
}

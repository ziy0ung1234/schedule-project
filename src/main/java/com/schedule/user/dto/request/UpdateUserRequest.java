package com.schedule.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 사용자 정보 수정 요청 DTO 클래스입니다.
 * <p>
 * 사용자가 자신의 정보를 수정할 때 전달하는 데이터 구조를 정의합니다.
 * <br>
 * 이름(username), 이메일(email)은 선택적으로 수정할 수 있으며,
 * 비밀번호(password)는 항상 필수로 입력해야 합니다.
 * </p>
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>username</b> – 변경할 사용자 이름 (선택, 최대 30자)</li>
 *   <li><b>email</b> – 변경할 이메일 (선택, 최대 50자)</li>
 *   <li><b>password</b> – 사용자 비밀번호 (필수, 본인 검증용)</li>
 * </ul>
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

package com.schedule.user.entity;

import com.schedule.global.entity.BaseEntity;
import com.schedule.global.validator.OwnedPassword;
import com.schedule.global.validator.OwnedUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 사용자(User) 엔티티 클래스입니다.
 * <p>
 * 회원의 기본 정보(이름, 이메일, 비밀번호)를 관리하며,
 * {@link BaseEntity}를 상속받아 생성일 및 수정일을 포함합니다.
 * </p>
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>id</b> – 사용자 고유 식별자 (PK, AUTO_INCREMENT)</li>
 *   <li><b>username</b> – 사용자 이름 (unique, 최대 30자)</li>
 *   <li><b>email</b> – 사용자 이메일 (unique, 최대 50자)</li>
 *   <li><b>password</b> – 비밀번호 (암호화 대상, null 불가)</li>
 * </ul>
 *
 * <h2>비즈니스 메서드</h2>
 * <ul>
 *   <li>{@link #updateUsername(String)} – 사용자 이름 변경</li>
 *   <li>{@link #updateEmail(String)} – 이메일 변경</li>
 *   <li>{@link #getPassword()} – {@code PasswordValidator} 구현체로서 비밀번호 반환</li>
 * </ul>
 */
@Getter
@Entity
@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements OwnedPassword,OwnedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=30,nullable = false, unique = true)
    private String username;
    @Column(length=50,nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    @Override
    public User getUser() {
        return this;
    }
    @Override
    public String getPassword() {
        return  password;
    }
    public void updateUsername(@Size(max=30) String username) {
        this.username = username;
    }
    public void updateEmail(@Size(max=50) String email) {
        this.email = email;
    }
}

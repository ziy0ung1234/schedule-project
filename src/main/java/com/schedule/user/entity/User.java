package com.schedule.user.entity;

import com.schedule.global.entity.BaseEntity;
import com.schedule.global.validator.OwnedPassword;
import com.schedule.global.validator.OwnedUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 유저 엔티티.
 * <p>이름, 이메일, 비밀번호를 관리하며 {@link BaseEntity}의 공통 필드를 상속한다.</p>
 */
@Getter
@Entity
@Table(name="user")
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

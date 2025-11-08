package com.schedule.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

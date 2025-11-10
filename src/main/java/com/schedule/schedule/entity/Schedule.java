package com.schedule.schedule.entity;

import com.schedule.global.entity.BaseEntity;
import com.schedule.global.validator.PasswordValidator;
import com.schedule.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name="schedules")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Schedule  extends BaseEntity implements PasswordValidator {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Schedule(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}
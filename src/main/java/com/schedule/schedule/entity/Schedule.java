package com.schedule.schedule.entity;

import com.schedule.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name="schedules")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Schedule  extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length=50,nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String title;
    private String description;


    public Schedule(String title, String description, String username, String password) {
        this.title = title;
        this.description = description;
        this.username = username;
        this.password = password;
    }

    public void updateTitle(String title) {
        this.title = title;
    }
    public void updateUsername(String username) {
        this.username = username;
    }
}
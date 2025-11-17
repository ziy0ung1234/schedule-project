package com.schedule.schedule.entity;

import com.schedule.global.entity.BaseEntity;
import com.schedule.global.util.OwnedPassword;
import com.schedule.global.util.OwnedUserId;
import com.schedule.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * 일정 엔티티.
 * <p>제목, 내용, 작성자 정보를 포함하며 {@link User}와 N:1 관계를 가진다.</p>
 */
@Getter
@Entity
@Table(name="schedule")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Schedule  extends BaseEntity implements OwnedPassword, OwnedUserId {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action= OnDeleteAction.CASCADE)
    private User user;

    public Schedule(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }
    @Override
    public Long getUserId() {
        return user.getId();
    }
    @Override
    public String getPassword(){
        return user.getPassword();
    }

    public void updateTitle(String title) {
        this.title = title;
    }
    public void updateDescription(String description) {
        this.description = description;
    }

}
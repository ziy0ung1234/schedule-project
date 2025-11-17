package com.schedule.comment.entity;

import com.schedule.global.entity.BaseEntity;
import com.schedule.global.util.OwnedPassword;
import com.schedule.global.util.OwnedUserId;
import com.schedule.schedule.entity.Schedule;
import com.schedule.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


/**
 * 댓글 엔티티 클래스입니다.
 * <p>
 * 사용자가 일정에 작성한 댓글 정보를 관리합니다.
 * <br>
 * {@link User} 엔티티와 다대일 관계를 가지며,
 * {@link Schedule} 엔티티와도 다대일 관계를 가집니다.
 * </p>
 */
@Getter
@Entity
@Table(name="comment")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Comment  extends BaseEntity implements OwnedPassword, OwnedUserId {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Schedule schedule;
    public Comment(String content, User user, Schedule schedule) {
        this.content =content;
        this.user = user;
        this.schedule = schedule;
    }
    @Override
    public Long getUserId() {
        return user.getId();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    public void updateContent(String content) {
        this.content = content;
    }
}
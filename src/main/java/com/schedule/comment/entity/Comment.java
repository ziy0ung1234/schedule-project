package com.schedule.comment.entity;

import com.schedule.global.entity.BaseEntity;
import com.schedule.global.validator.OwnedPassword;
import com.schedule.global.validator.OwnedUser;
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
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>id</b> – 댓글의 고유 식별자 (PK, auto increment)</li>
 *   <li><b>content</b> – 댓글 본문 내용 (필수 입력)</li>
 *   <li><b>user</b> – 댓글 작성자 엔티티 참조 (N:1 관계)</li>
 *   <li><b>schedule</b> – 댓글이 속한 일정 엔티티 참조 (N:1 관계)</li>
 * </ul>
 *
 * <h2>비즈니스 메서드</h2>
 * <ul>
 *   <li>{@link #getPassword()} – 비밀번호 검증을 위한 사용자 비밀번호 반환</li>
 *   <li>{@link #updateContent(String)} – 댓글 내용을 수정</li>
 * </ul>
 */
@Getter
@Entity
@Table(name="comment")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Comment  extends BaseEntity implements OwnedPassword,OwnedUser {
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
    public User getUser() {
        return user;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    public void updateContent(String content) {
        this.content = content;
    }
}
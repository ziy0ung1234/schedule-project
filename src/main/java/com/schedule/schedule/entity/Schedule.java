package com.schedule.schedule.entity;

import com.schedule.global.entity.BaseEntity;
import com.schedule.global.validator.OwnedPassword;
import com.schedule.global.validator.OwnedUser;
import com.schedule.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * 일정(Schedule) 엔티티 클래스입니다.
 * <p>
 * 사용자가 등록한 일정의 기본 정보(제목, 내용)와 작성자(User) 정보를 관리합니다.
 * <br>
 * {@link com.schedule.user.entity.User} 엔티티와 다대일(N:1) 관계를 가집니다.
 * </p>
 *
 * <h2>주요 특징</h2>
 * <ul>
 *   <li>{@code BaseEntity}를 상속받아 생성일/수정일 등의 공통 필드 관리</li>
 *   <li>{@code PasswordValidator} 인터페이스를 구현해 사용자 비밀번호 검증 책임 위임</li>
 *   <li>지연 로딩(LAZY)을 적용하여 User 엔티티와 효율적으로 연관 관리</li>
 * </ul>
 *
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>id</b> – 일정의 고유 식별자 (PK, auto increment)</li>
 *   <li><b>title</b> – 일정 제목</li>
 *   <li><b>description</b> – 일정 내용</li>
 *   <li><b>user</b> – 작성자(User) 엔티티 참조 (N:1 관계)</li>
 * </ul>

 * <h2>비즈니스 메서드</h2>
 * <ul>
 *   <li>{@link #getPassword()} – 비밀번호 검증을 위한 사용자 비밀번호 반환</li>
 *   <li>{@link #updateTitle(String)} – 일정 제목 수정</li>
 * </ul>
 */
@Getter
@Entity
@Table(name="schedules")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Schedule  extends BaseEntity implements OwnedPassword,OwnedUser {
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
    public User getUser() {
        return user;
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
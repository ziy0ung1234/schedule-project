package com.schedule.user.repository;

import com.schedule.global.exception.CustomException;
import com.schedule.global.exception.ErrorMessage;
import com.schedule.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * {@link User} 엔티티의 데이터 접근 계층을 담당하는 JPA 리포지토리 인터페이스입니다.
 * <p>
 * 사용자 정보의 CRUD 및 중복 검증, 이메일 기반 조회 등을 제공합니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li>이메일 중복 여부 확인: {@link #existsByEmail(String)}</li>
 *   <li>사용자명 중복 여부 확인: {@link #existsByUsername(String)}</li>
 *   <li>이메일로 사용자 조회: {@link #findByEmail(String)}</li>
 * </ul>
 */
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);

    default User findOrException(Long id) {
        return findById(id)
                .orElseThrow(()-> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }
}

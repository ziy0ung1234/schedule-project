package com.schedule.user.repository;

import com.schedule.global.exception.CustomException;
import com.schedule.global.exception.ErrorMessage;
import com.schedule.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 사용자 엔티티 리포지토리.
 * <p>기본 CRUD 외에 이메일·이름 중복 검사 기능을 제공한다.</p>
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

package com.schedule.global.validator;
import com.schedule.global.config.PasswordEncoder;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * 공통 유효성 검증 로직을 제공하는 유틸리티 컴포넌트 클래스입니다.
 * <p>
 * 서비스 계층에서 자주 사용되는 검증 로직(엔티티 존재 여부, 비밀번호 일치 여부)을 공통화하여
 * 코드 중복을 줄이고, 일관된 예외 메시지를 제공합니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li>{@link #findOrException(JpaRepository, Long)} – 엔티티 존재 여부 검증</li>
 *   <li>{@link #matchPassword(PasswordValidator, String)} – 비밀번호 일치 검증</li>
 * </ul>
 */
@Component
public class GlobalValidator {
    PasswordEncoder passwordEncoder = new PasswordEncoder();
    /**
     * 공통 엔티티 조회 메서드
     *
     * @param repository 조회에 사용할 JPA Repository
     * @param id 조회할 엔티티의 ID
     * @return 존재하는 엔티티 반환, 없으면 예외
     */
    public <T> T findOrException(JpaRepository<T, Long> repository, Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 데이터입니다."));
    }
    public <T extends PasswordValidator> void matchPassword(T entity, String password) {
        boolean isMatched = passwordEncoder.matches(password, entity.getPassword());
        if (!isMatched) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}

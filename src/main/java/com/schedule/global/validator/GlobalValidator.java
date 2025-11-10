package com.schedule.global.validator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GlobalValidator {


    /**
     * 공통 엔티티 조회 메서드
     *
     * @param repository 조회에 사용할 JPA Repository
     * @param id 조회할 엔티티의 ID
     * @return 존재하는 엔티티 반환, 없으면 예외
     */
    public <T> T findOrException(JpaRepository<T, Long> repository, Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 데이터입니다."));
    }
    public <T extends PasswordValidator> void validatePassword(T entity, String password) {
        if (!Objects.equals(entity.getPassword(), password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}

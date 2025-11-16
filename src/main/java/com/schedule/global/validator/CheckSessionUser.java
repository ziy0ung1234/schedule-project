package com.schedule.global.validator;
import com.schedule.global.exception.CustomException;
import com.schedule.global.exception.ErrorMessage;
import org.springframework.stereotype.Component;

/**
 * 세션 사용자 검증 유틸리티 클래스
 * <p>엔티티의 작성자와 세션 사용자가 다를 경우 접근을 제한한다.</p>
 */
@Component
public class CheckSessionUser {
    public <T extends OwnedUser>void forbiddenUserHandler(T entity, Long userId) {
        if (!entity.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorMessage.FORBIDDEN);
        }
    }
}

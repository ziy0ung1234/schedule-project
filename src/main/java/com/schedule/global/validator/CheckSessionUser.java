package com.schedule.global.validator;
import com.schedule.global.exception.CustomException;
import com.schedule.global.exception.ErrorMessage;
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

 * </ul>
 */
@Component
public class CheckSessionUser {
    public <T extends OwnedUser>void forbiddenUserHandler(T entity, Long userId) {
        if (!entity.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorMessage.FORBIDDEN);
        }
    }
}

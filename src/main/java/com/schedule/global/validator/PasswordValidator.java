package com.schedule.global.validator;

/**
 * 비밀번호 검증을 위한 공통 인터페이스입니다.
 * <p>
 * 엔티티가 이 인터페이스를 구현하면,
 * {@link com.schedule.global.validator.GlobalValidator}에서
 * 비밀번호 일치 여부를 일관되게 검증할 수 있습니다.
 * </p>
 *
 * <h2>설계 목적</h2>
 * <ul>
 *   <li>비밀번호 접근 방식을 통일하여 도메인 간 검증 로직 중복 제거</li>
 *   <li>{@link com.schedule.global.validator.GlobalValidator#matchPassword(PasswordValidator, String)}
 *       에서 모든 엔티티를 공통 처리 가능하게 함</li>
 *   <li>각 도메인이 자신의 비밀번호 소스를 직접 정의하도록 책임 부여</li>
 * </ul>
 */
public interface PasswordValidator {
    String getPassword();
}

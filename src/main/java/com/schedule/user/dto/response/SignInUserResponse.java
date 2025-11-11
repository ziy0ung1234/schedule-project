package com.schedule.user.dto.response;
/**
 * 사용자 로그인 응답 DTO 클래스입니다.
 * <p>
 * 로그인 요청 처리 결과를 클라이언트로 전달합니다.
 * 주로 로그인 실패(401 Unauthorized) 시 이메일과 실패 원인 메시지를 함께 반환합니다.
 * </p>
 * <h2>설계 이유</h2>
 * <ul>
 *   <li>{@code record}를 사용하여 <b>불변(immutable)</b>하고 <b>간결한</b> 데이터 전달 객체로 구현</li>
 *   <li>로그인 응답은 상태 변경이 필요 없는 단순 데이터이므로, 클래스를 만드는 것보다 record가 효율적</li>
 *   <li>자동으로 {@code equals}, {@code hashCode}, {@code toString}이 생성되어 유지보수 용이</li>
 * </ul>
 * <h2>필드 설명</h2>
 * <ul>
 *   <li><b>email</b> – 로그인 시도한 사용자 이메일</li>
 *   <li><b>message</b> – 로그인 실패 사유 메시지</li>
 * </ul>
 */
public record SignInUserResponse(String email, String message) {}


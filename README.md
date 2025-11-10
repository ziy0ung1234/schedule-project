# 📋 프로젝트 개요
지금까지 배운 개념들을 적용해서 필수5,도전4단계로 나누어 객체지향적 코드 설계에 중점을 둔 스프링 프로젝트 입니다.
# 주요 기능
이번 프로젝트는 브랜치별로 나누지 않고 커밋 머릿말로만 단순하게 나누어 작업했습니다.
## [Level1]
- Schedule 엔티티: title, description, username, createdAt, modifiedAt
- BaseEntity 추상 클래스에 Auditing 설정 (`@MappedSuperclass`, `@EntityListeners(AuditingEntityListener.class)`)
- Controller → Service → Repository 3계층 구조
- `@Transactional(readOnly = true)` 로 조회 성능 최적화
- JSON Body 유효성 검증(`@NotBlank`,`@Size`)
## [Level2]
- User 엔티티 : username, email, createdAt, modifiedAt
- Schedule ↔ User 단방향 연관관계 (Schedule → User) `@ManyToOne`
- 기존 Schedule 엔티티의 username 필드 → user_id 로 변경
- UserRepository, UserService, UserController 추가
## [Level3]
- User 엔티티에 password 필드 추가
- 이메일 중복 검사 (findByEmail)
- 예외처리: IllegalArgumentException → GlobalExceptionHandler 에서 BAD_REQUEST 반환
## [Level4]
- 로그인 시 이메일 + 비밀번호로 사용자 인증
- 성공 시 session.setAttribute("userId", user.getId())
- 모든 API 호출 전에 LoginFilter 가 세션 검증
  - /signup, /signin 은 예외 경
- 로그인 안 된 상태에서 접근 시
```json
{
  "code": "UNAUTHORIZED",
  "message": "로그인이 필요합니다."
}
```
- 예외는 GlobalExceptionHandler 대신 Filter 내부에서 직접 처리 (Controller 이전 단계이기 때문)
- 추가적으로 로그아웃 기능도 구현
- 성공시 session.removeAttribute("userId")
## [Level5]
## [Level6]
## [Level7]
## [Level8]

# ERD
![ERD image](https://github.com/user-attachments/assets/d54e09c8-d026-4d0f-a564-0ea3bb1a6d69)

# API 명세
엔드포인트가 많은 관계로 노션 링크로 첨부합니다.
[API 명세 정리 Notion](https://www.notion.so/API-2a6dc7ecfa41803abe41e1a99c31f0d7?source=copy_link)

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
- Schedule은 user_id를 FK로 참조
- UserRepository, UserService, UserController 추가
## [Level3]
- 회원가입 요청
  - POST /signup 엔드포인트로 username, email, password 요청
  - DTO 검증:
    - 이메일 형식 (`@Email`)
    - 공백 불가 (`@NotBlank`)
    - 비밀번호 길이 제한 (`@Size`)
- 유효성 검증
  - UserRepository.findByEmail(email) 로 중복 이메일 검사
  - 이미 존재 시 예외 발생 →
```json
{
  "code": "BAD_REQUEST",
  "message": "이미 등록된 이메일입니다."
}
```
- 데이터 저장
  - 검증 통과 시 User 엔티티로 변환 후 저장
  - `BaseEntity` 상속으로 createdAt, modifiedAt 자동 기록
- 예외 처리
  - 전역 예외 핸들러(`@ControllerAdvice`)에서 IllegalArgumentException 처리
  - HTTP Status 400 + JSON 에러 메시지 반환
- User 엔티티에 password 필드 추가
- 이메일 중복 검사 (`findByEmail`)
- 예외처리: IllegalArgumentException → GlobalExceptionHandler 에서 BAD_REQUEST 반환
## [Level4]
- 로그인
  - POST /signin 요청 시 이메일 + 비밀번호 검증
  - 성공 시 HttpSession 생성 → userId 저장
  - 실패 시 401 Unauthorized + 오류 메시지 반환
- 로그인 인증 Filter
  - LoginFilter로 모든 요청 가로채기
  - /signup, /signin은 예외 경로
  - 세션 없거나 userId 미존재 시
```json
{
  "code": "UNAUTHORIZED",
  "message": "로그인이 필요합니다."
}
```
반환
- 로그인 유지
  - `JSESSIONID` 쿠키로 브라우저 세션 식별
  - 세션이 유지되는 동안 일정/유저 API 접근 가능
- 예외 처리
  - Filter 내부 예외는 JSON 직접 작성 (Controller 이전 단계이기 때문)
## [Level5]
 - DTO Validation 어노테이션 `@NotBlank`,`@Size`,`@Email`,`@Pattern` 등을 활용해 입력값 검증
 - 프로젝트 전반에서 발생 가능한 예외를 찾아 직접 예외 항목 정의(Error Message)
 - 예시
    - 일정 제목 최대 30자
    - 비밀번호, 댓글 내용 필수 값 검증
 - `@RestControllerAdvice` + `CustomException` + `ErrorMessage` Enum 으로 전역 예외 처리 구현
 - 검증 실패 시 통일된 응답 포맷(JSON)으로 에러 필드와 사유 반환
 - 불필요한 중복 로직 제거, 도메인별 명확한 예외 메시지 제공
## [Level6]
- 직접 구현한 `PasswordEncoder`를 사용해 비밀번호 암호화/검증 처리
- `at.favre.lib:bcrypt:0.10.2` 의존성 추가로 `BCrypt` 해시 알고리즘 적용
- 회원가입 시 비밀번호 암호화 저장
- 로그인 및 수정/삭제 시 암호 일치 여부 검증
- 암호화된 비밀번호는 데이터베이스에 그대로 저장되어 복호화 불가능
## [Level7]
- 단방향 매핑 구조 적용
    - Comment - Schedule, Comment - User (ManyToOne)
    - Schedule과 User는 Comment를 알지 못함 → 의존성 최소화
- 댓글 생성, 단건 조회, 전체 조회, 수정, 삭제 기능 구현
- 댓글 작성자와 로그인 사용자(Session userId)가 다를 경우 AccessDeniedException 발생
- 댓글 단건 조회 시 본인 작성 댓글만 접근 가능
- 일정 단건 조회 시 해당 일정의 댓글 목록을 함께 반환 `findAllByScheduleIdOrderByCreatedAtDesc`
- BaseEntity의 @CreatedDate, @LastModifiedDate로 작성일/수정일 자동 관리
## [Level8]
- Spring Data JPA의 Pageable, Page 인터페이스 활용
- findByUserIdOrderByCreatedAtDesc()로 로그인 유저 기준 일정 페이징 조회
- PageScheduleResponse DTO를 통해
    - 일정 목록`List<GetAllScheduleResponse>` + 페이지 정보(totalPages, totalElements 등) 함께 반환
- 일정 목록 조회 시 각 일정의 댓글 개수`countAllByScheduleIdOrderByCreatedAtDesc` 포함
- 기본 페이지 크기 10, 수정일 기준 내림차순 정렬 적용
- Pageable 정보(page, size)는 쿼리 파라미터로 전달
## 추가 구현사항
# ERD
![ERD image](https://github.com/user-attachments/assets/d54e09c8-d026-4d0f-a564-0ea3bb1a6d69)

# API 명세
엔드포인트가 많은 관계로 노션 링크로 첨부합니다.
[API 명세 정리 Notion](https://www.notion.so/API-2a6dc7ecfa41803abe41e1a99c31f0d7?source=copy_link)

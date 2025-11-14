package com.schedule.user.controller;

import com.schedule.user.dto.request.*;
import com.schedule.user.dto.response.*;
import com.schedule.user.entity.User;
import com.schedule.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 사용자(User) 관련 REST API를 제공하는 컨트롤러 클래스입니다.
 * <p>
 * 회원가입, 로그인, 로그아웃을 비롯해 사용자 CRUD 기능을 관리합니다.
 * <br>
 * 로그인은 {@link HttpSession}을 활용한 세션 기반 인증으로 처리됩니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li>회원가입: {@link #signUpUser(CreateUserRequest)}</li>
 *   <li>로그인: {@link #signInUser(SignInUserRequest, HttpServletRequest)}</li>
 *   <li>로그아웃: {@link #logoutUser(HttpServletRequest)}</li>
 *   <li>사용자 생성 (관리자용): {@link #createUser(CreateUserRequest)}</li>
 *   <li>단일 사용자 조회: {@link #myUser(HttpServletRequest)}</li>
 *   <li>사용자 수정: {@link #updateUser(UpdateUserRequest,HttpServletRequest)}</li>
 *   <li>사용자 삭제: {@link #deleteUser(DeleteUserRequest,HttpServletRequest)}</li>
 * </ul>
 */
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //------- 회원가입/로그인/로그아웃 --------
    /**
     * 회원가입
     *
     * @param request 회원가입 요청 DTO
     * @return 생성된 사용자 정보
     */
    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> signUpUser (@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request));
    }
    /**
     * 로그인
     * <p>
     * 요청이 성공하면 세션에 {@code userId}를 저장하고, 실패 시 401 응답을 반환합니다.
     * </p>
     *
     * @param request 로그인 요청 DTO
     * @param httpRequest Http 요청 (세션 접근용)
     * @return 204 (로그인 성공) 또는 401 (비밀번호 불일치 등)
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(
            @Valid @RequestBody SignInUserRequest request,
            HttpServletRequest httpRequest
    ) {
        try {
            User user= userService.signIn(request);
            HttpSession session = httpRequest.getSession(); // 기존 세션 있으면 재사용, 없으면 새로 생성
            session.setAttribute("userId", user.getId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 세션 로그인 성공
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new SignInUserResponse(request.getEmail(), e.getMessage()));
        }
    }
    /**
     * 로그아웃
     * <p>
     * 세션에서 {@code userId} 속성을 제거합니다.
     * </p>
     *
     * @param httpRequest Http 요청 (세션 접근용)
     * @return 204 (성공적으로 로그아웃)
     */
    @PostMapping("/signout")
    public ResponseEntity<Void> logoutUser(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        session.removeAttribute("userId");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //------ 이외 user 관련 메서드 --------
    //TODO 인가 구현 후 관리자용 사용자 생성
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser (@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }
    @GetMapping("/users/me")
    public ResponseEntity<GetOneUserResponse> myUser (HttpServletRequest httpRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(sessionUserId(httpRequest)));
    }
    @PatchMapping("/users/me/update")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @RequestBody UpdateUserRequest request,
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(sessionUserId(httpRequest), request));
    }
    @PostMapping("/users/me/delete")
    public ResponseEntity<Void> deleteUser(
            @Valid @RequestBody DeleteUserRequest request,
            HttpServletRequest httpRequest
    ) {
        userService.delete(sessionUserId(httpRequest),request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //-------------공용 메서드 ----------------
    public Long sessionUserId (HttpServletRequest httpRequest) {
        return(Long) httpRequest.getSession().getAttribute("userId");
    }
}

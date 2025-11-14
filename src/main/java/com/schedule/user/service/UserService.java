package com.schedule.user.service;

import com.schedule.global.config.PasswordEncoder;
import com.schedule.global.exception.CustomException;
import com.schedule.global.exception.ErrorMessage;
import com.schedule.global.validator.GlobalValidator;
import com.schedule.user.dto.request.*;
import com.schedule.user.dto.response.*;
import com.schedule.user.entity.User;
import com.schedule.user.dto.response.CreateUserResponse;
import com.schedule.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 사용자(User) 관련 비즈니스 로직을 담당하는 서비스 클래스입니다.
 * <p>
 * 회원가입, 로그인, 사용자 조회·수정·삭제 기능을 트랜잭션 단위로 처리합니다.
 * <br>
 * 데이터 유효성 및 비밀번호 검증은 {@link GlobalValidator}를 통해 수행합니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li>회원가입: {@link #signUp(CreateUserRequest)}</li>
 *   <li>로그인: {@link #signIn(SignInUserRequest)}</li>
 *   <li>단일 사용자 조회: {@link #findOne(Long)}</li>
 *   <li>사용자 정보 수정: {@link #update(Long, UpdateUserRequest)}</li>
 *   <li>사용자 삭제: {@link #delete(Long, DeleteUserRequest)}</li>
 * </ul>
 *
 * <h2>트랜잭션 정책</h2>
 * <ul>
 *   <li>{@code @Transactional(readOnly = true)} — 조회 로직 최적화</li>
 *   <li>{@code @Transactional} — 생성, 수정, 삭제 시 데이터 일관성 보장</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GlobalValidator globalValidator;
    PasswordEncoder passwordEncoder = new PasswordEncoder();

    @Transactional
    public CreateUserResponse signUp(@Valid CreateUserRequest request) {
        //-----DB 조건 검증 (unique)-------
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(ErrorMessage.EXIST_USERNAME);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorMessage.EXIST_EMAIL);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                encodedPassword
        );
        User signUpUser = userRepository.save(user);
        return new CreateUserResponse(
                signUpUser.getId(),
                signUpUser.getUsername(),
                signUpUser.getEmail(),
                signUpUser.getCreatedAt(),
                signUpUser.getModifiedAt()
        );
    }
    @Transactional
    public User signIn(@Valid SignInUserRequest request ) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));

        // 비밀번호 검증
        matchPassword(user, request.getPassword());
        return user;
    }

    @Transactional
    public CreateUserResponse save(@Valid CreateUserRequest request) {
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }
    @Transactional(readOnly = true)
    public GetOneUserResponse findOne(Long userId) {
        User user = userRepository.findOrException(userId);
        return new GetOneUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findOrException(userId);
        // 이메일 DB 조건 검증 (unique)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorMessage.EXIST_EMAIL);
        }
        globalValidator.forbiddenErrorHandler(user, userId);
        // 비밀번호 검증
        matchPassword(user, request.getPassword());
        // 선택적 수정
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.updateEmail(request.getEmail());
        }
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            user.updateUsername(request.getUsername());
        }
        userRepository.flush();
        return new UpdateUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getModifiedAt()
        );
    }
    @Transactional
    public void delete(Long userId, DeleteUserRequest request) {
        User user = userRepository.findOrException(userId);
        globalValidator.forbiddenErrorHandler(user, userId);
        matchPassword(user, request.getPassword());
        userRepository.deleteById(userId);
    }

    public void matchPassword(User user, String password) {
        boolean isMatched = passwordEncoder.matches(password, user.getPassword());
        if (!isMatched) {
            throw new CustomException(ErrorMessage.NOT_MATCHED_PASSWORD);
        }
    }
}

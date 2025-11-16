package com.schedule.user.service;

import com.schedule.global.config.PasswordEncoder;
import com.schedule.global.exception.CustomException;
import com.schedule.global.exception.ErrorMessage;
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
 * 유저 관련 비즈니스 로직 서비스.
 * <p>회원가입, 로그인, 조회, 수정, 삭제를 처리한다.</p>
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
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
        return new CreateUserResponse(signUpUser);
    }
    @Transactional
    public User signIn(@Valid SignInUserRequest request ) {
        // 이메일로 유저 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));

        // 비밀번호 검증
        matchPassword(user, request.getPassword());
        return user;
    }
    //Todo 관리자용 유저 생성
    @Transactional
    public CreateUserResponse save(@Valid CreateUserRequest request) {
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(savedUser);
    }
    @Transactional(readOnly = true)
    public GetOneUserResponse findOne(Long userId) {
        User user = userRepository.findOrException(userId);
        return new GetOneUserResponse(user);
    }

    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findOrException(userId);
        // 이메일 DB 조건 검증 (unique)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorMessage.EXIST_EMAIL);
        }
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
        return new UpdateUserResponse(user);
    }
    @Transactional
    public void delete(Long userId, DeleteUserRequest request) {
        User user = userRepository.findOrException(userId);
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

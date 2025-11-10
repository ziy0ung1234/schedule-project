package com.schedule.user.service;

import com.schedule.global.validator.GlobalValidator;
import com.schedule.user.dto.request.DeleteUserRequest;
import com.schedule.user.dto.request.UpdateUserRequest;
import com.schedule.user.dto.response.*;
import com.schedule.user.entity.User;
import com.schedule.user.dto.request.CreateUserRequest;
import com.schedule.user.dto.response.CreateUserResponse;
import com.schedule.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GlobalValidator globalValidator;

    @Transactional
    public CreateUserResponse signUp(@Valid CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
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
    public List<GetOneUserResponse> findAll() {
        List<User> users = userRepository.findAllByOrderByCreatedAtDesc();
        return users.stream()
                .map(user -> new GetOneUserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                ))
                .toList();
    }
    @Transactional(readOnly = true)
    public GetOneUserResponse findOne(Long userId) {
        User user = globalValidator.findOrException(userRepository,userId);

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
        User user = globalValidator.findOrException(userRepository,userId);
        // 비밀번호 검증
        globalValidator.validatePassword(user, request.getPassword());
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
        User user = globalValidator.findOrException(userRepository,userId);
        globalValidator.validatePassword(user, request.getPassword());
        userRepository.deleteById(userId);
    }
    
}

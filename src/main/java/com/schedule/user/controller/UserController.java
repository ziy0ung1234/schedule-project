package com.schedule.user.controller;

import com.schedule.user.dto.request.*;
import com.schedule.user.dto.response.*;
import com.schedule.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> signUpUser (@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request));
    }
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser (@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetOneUserResponse> getOneUser (@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }
    @GetMapping("/users")
    public ResponseEntity<List<GetOneUserResponse>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }
    @PatchMapping("/users/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, request));
    }
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, @Valid @RequestBody DeleteUserRequest request) {
        userService.delete(userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

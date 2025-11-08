package com.schedule.user.service;

import com.schedule.global.validator.GlobalValidator;
import com.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GlobalValidator globalValidator;
}

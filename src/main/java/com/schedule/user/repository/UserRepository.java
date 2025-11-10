package com.schedule.user.repository;

import com.schedule.user.entity.User;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    List<User> findAllByOrderByCreatedAtDesc();
    Optional<User> findByEmail(String email);
}

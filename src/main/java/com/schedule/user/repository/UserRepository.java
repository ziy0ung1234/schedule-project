package com.schedule.user.repository;

import com.schedule.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    List<User> findAllByOrderByCreatedAtDesc();
}

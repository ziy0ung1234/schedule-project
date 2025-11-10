package com.schedule.schedule.repository;

import com.schedule.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findById(Long id);    // 전체 일정 조회, 수정일 기준 내림차순
    // SELECT s FROM Schedule s ORDER BY s.createdAt DESC
    List<Schedule> findAllByOrderByCreatedAtDesc();

    // 특정 작성자(username) 일정 조회, 수정일 기준 내림차순
    // SELECT s FROM Schedule s WHERE s.username = :username ORDER BY s.createdAt DESC
    // ScheduleRepository.java
    @Query("SELECT s FROM Schedule s WHERE s.user.username = :username ORDER BY s.createdAt DESC")
    List<Schedule> findAllByUserUsernameOrderByCreatedAtDesc(@Param("username") String username);


    void deleteById(Long scheduleId);
}

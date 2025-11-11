package com.schedule.schedule.repository;

import com.schedule.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * {@link Schedule} 엔티티의 데이터 접근을 담당하는 JPA 리포지토리 인터페이스입니다.
 * <p>
 * 기본적인 CRUD 기능을 {@link JpaRepository}에서 상속받고,
 * 추가적으로 일정 정렬, 작성자별 조회 등 커스텀 쿼리 메서드를 제공합니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li>일정 단건 조회: {@link #findById(Long)}</li>
 *   <li>전체 일정 조회 (최신순): {@link #findAllByOrderByCreatedAtDesc()}</li>
 *   <li>일정 삭제: {@link #deleteById(Long)}</li>
 * </ul>
 */

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findById(Long id);    // 전체 일정 조회, 수정일 기준 내림차순
    // SELECT s FROM Schedule s ORDER BY s.createdAt DESC
    List<Schedule> findAllByOrderByCreatedAtDesc();
    void deleteById(Long scheduleId);
}

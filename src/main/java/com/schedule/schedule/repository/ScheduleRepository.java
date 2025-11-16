package com.schedule.schedule.repository;

import com.schedule.global.exception.CustomException;
import com.schedule.global.exception.ErrorMessage;
import com.schedule.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * 일정 엔티티 리포지토리.
 * <p>기본 CRUD와 정렬·페이지네이션 기능을 제공한다.</p>
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findById(Long id);    // 전체 일정 조회, 수정일 기준 내림차순
    // SELECT s FROM Schedule s ORDER BY s.createdAt DESC
    List<Schedule> findAllByOrderByCreatedAtDesc();
    void deleteById(Long scheduleId);
    Page<Schedule> findAllByOrderByCreatedAtDesc(Pageable pageable);

    default Schedule findOrException(Long id) {
        return findById(id)
                .orElseThrow(()-> new CustomException(ErrorMessage.NOT_FOUND_SCHEDULE));
    }
}

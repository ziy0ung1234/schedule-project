package com.schedule.comment.repository;

import com.schedule.comment.entity.Comment;
import com.schedule.global.exception.CustomException;
import com.schedule.global.exception.ErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * {@link Comment} 엔티티의 데이터 접근을 담당하는 JPA 리포지토리 인터페이스입니다.
 * <p>
 * 기본적인 CRUD 기능을 {@link JpaRepository}에서 상속받으며,
 * 댓글의 정렬, 일정별 조회 등 커스텀 쿼리 메서드를 제공합니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li><b>단건 조회</b>: {@link #findOrException(Long)} – 존재하지 않을 경우 {@code CustomException} 발생</li>
 *   <li><b>전체 댓글 조회</b>: {@link #findAllByOrderByCreatedAtDesc()} – 최신 생성순 정렬</li>
 *   <li><b>일정별 댓글 조회</b>: {@link #findAllByScheduleIdOrderByModifiedAtDesc(Long)} – 수정일 기준 내림차순 정렬</li>
 *   <li><b>댓글 수 카운트</b>: {@link #countAllByScheduleId(Long)} – 일정별 총 댓글 수 반환</li>
 *   <li><b>댓글 삭제</b>: {@link #deleteById(Long)}</li>
 * </ul>
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);    // 전체 댓글 조회, 수정일 기준 내림차순
    // SELECT s FROM Schedule s ORDER BY s.createdAt DESC
    List<Comment> findAllByOrderByCreatedAtDesc();
    void deleteById(Long commentId);
    List<Comment> findAllByScheduleIdOrderByModifiedAtDesc(Long scheduleId);
    int countAllByScheduleId(Long scheduleId);

    default Comment findOrException(Long id) {
        return findById(id)
                .orElseThrow(()-> new CustomException(ErrorMessage.NOT_FOUND_COMMENT));
    }
}

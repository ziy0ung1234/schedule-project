package com.schedule.schedule.service;

import com.schedule.global.config.PasswordEncoder;
import com.schedule.global.exception.CustomException;
import com.schedule.global.exception.ErrorMessage;
import com.schedule.schedule.dto.response.GetAllScheduleResponse;
import com.schedule.comment.dto.response.GetOneCommentResponse;
import com.schedule.comment.entity.Comment;
import com.schedule.comment.repository.CommentRepository;
import com.schedule.schedule.dto.request.*;
import com.schedule.schedule.dto.response.*;
import com.schedule.schedule.entity.Schedule;
import com.schedule.schedule.repository.ScheduleRepository;
import com.schedule.global.validator.GlobalValidator;
import com.schedule.user.entity.User;
import com.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 일정(Schedule) 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * <p>
 * 컨트롤러 계층과 리포지토리 계층을 연결하며,
 * 트랜잭션 단위로 일정의 생성, 조회, 수정, 삭제를 담당합니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li>일정 생성: {@link #save(CreateScheduleRequest, Long)}</li>
 *   <li>전체 일정 조회: {@link #findAll(Pageable)}</li>
 *   <li>단일 일정 조회: {@link #findOne(Long)}</li>
 *   <li>일정 수정: {@link #update(Long, UpdateScheduleRequest,Long)}</li>
 *   <li>일정 삭제: {@link #delete(Long, DeleteScheduleRequest,Long)}</li>
 * </ul>
 *
 * <h2>트랜잭션 정책</h2>
 * <ul>
 *   <li>쓰기 작업(save, update, delete)은 {@code @Transactional}로 관리</li>
 *   <li>조회 작업(findAll, findOne)은 {@code @Transactional(readOnly = true)}로 성능 최적화</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final GlobalValidator globalValidator;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request, Long  userId) {
        User user = userRepository.findOrException(userId);
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getDescription(),
                user
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getUser().getUsername(),
                savedSchedule.getTitle(),
                savedSchedule.getDescription(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public PageScheduleResponse<GetAllScheduleResponse> findAll(Pageable pageable) {
       Page<Schedule> pagination = scheduleRepository.findAllByOrderByCreatedAtDesc(pageable);
       List<GetAllScheduleResponse> responses = pagination.getContent().stream()
               .map(schedule -> new GetAllScheduleResponse(
                       schedule.getId(),
                       schedule.getUser().getUsername(),
                       schedule.getTitle(),
                       schedule.getDescription(),
                       commentRepository.countAllByScheduleIdOrderByCreatedAtDesc(schedule.getId()),
                       schedule.getCreatedAt(),
                       schedule.getModifiedAt()
                       )
               ).toList();
       return new PageScheduleResponse<>(
               responses,
               pagination.getNumber(),
               pagination.getTotalPages(),
               pagination.getTotalElements(),
               pagination.isFirst(),
               pagination.isLast()
       );
    }
    @Transactional(readOnly = true)
    public GetOneScheduleResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findOrException(scheduleId);
        List<Comment> comments = commentRepository.findAllByScheduleIdOrderByCreatedAtDesc(scheduleId);
        List<GetOneCommentResponse> commentResponses = comments.stream()
                .map(GetOneCommentResponse::new)
                .toList();
        return new GetOneScheduleResponse(
                schedule.getId(),
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                commentResponses
        );
    }

    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request, Long  userId) {
        Schedule schedule = scheduleRepository.findOrException(scheduleId);
        globalValidator.forbiddenErrorHandler(schedule,userId);
        // 비밀번호 검증
        matchPassword(schedule, request.getPassword());
        // 선택적 수정
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            schedule.updateTitle(request.getTitle());
        }
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            schedule.getUser().updateUsername(request.getUsername());
        }
        scheduleRepository.flush();
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getUser().getUsername(),
                schedule.getModifiedAt()
        );
    }
    @Transactional
    public void delete(Long scheduleId, DeleteScheduleRequest request, Long userId) {
        Schedule schedule = scheduleRepository.findOrException(scheduleId);
        globalValidator.forbiddenErrorHandler(schedule,userId);
        matchPassword(schedule, request.getPassword());
        scheduleRepository.deleteById(scheduleId);
    }

    public void matchPassword(Schedule schedule, String password) {
        boolean isMatched = passwordEncoder.matches(password, schedule.getPassword());
        if (!isMatched) {
            throw new CustomException(ErrorMessage.NOT_MATCHED_PASSWORD);
        }
    }

}

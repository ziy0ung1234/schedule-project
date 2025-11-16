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
import com.schedule.global.validator.CheckSessionUser;
import com.schedule.user.entity.User;
import com.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 일정 관련 비즈니스 로직 서비스.
 * <p>생성, 조회, 수정, 삭제 및 댓글 연동 로직을 처리한다.</p>
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CheckSessionUser checkSessionUser;
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
        return new CreateScheduleResponse(savedSchedule);
    }

    @Transactional(readOnly = true)
    public PageScheduleResponse<GetAllScheduleResponse> findAll(Pageable pageable) {
       Page<Schedule> pagination = scheduleRepository.findAllByOrderByCreatedAtDesc(pageable);
       List<GetAllScheduleResponse> responses = pagination.getContent().stream()
               .map(schedule -> new GetAllScheduleResponse(
                       schedule,
                       commentRepository.countAllByScheduleId(schedule.getId())
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
        List<Comment> comments = commentRepository.findAllByScheduleIdOrderByModifiedAtDesc(scheduleId);
        List<GetOneCommentResponse> commentResponses = comments.stream()
                .map(GetOneCommentResponse::new)
                .toList();
        return new GetOneScheduleResponse(schedule, commentResponses);
    }

    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request, Long  userId) {
        Schedule schedule = scheduleRepository.findOrException(scheduleId);
        checkSessionUser.forbiddenUserHandler(schedule,userId);
        // 비밀번호 검증
        matchPassword(schedule, request.getPassword());
        // 선택적 수정
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            schedule.updateTitle(request.getTitle());
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            schedule.updateDescription(request.getDescription());
        }
        scheduleRepository.flush();
        return new UpdateScheduleResponse(schedule);
    }
    @Transactional
    public void delete(Long scheduleId, DeleteScheduleRequest request, Long userId) {
        Schedule schedule = scheduleRepository.findOrException(scheduleId);
        checkSessionUser.forbiddenUserHandler(schedule,userId);
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

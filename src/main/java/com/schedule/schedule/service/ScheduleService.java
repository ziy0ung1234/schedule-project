package com.schedule.schedule.service;

import com.schedule.schedule.dto.request.*;
import com.schedule.schedule.dto.response.*;
import com.schedule.schedule.entity.Schedule;
import com.schedule.schedule.repository.ScheduleRepository;
import com.schedule.global.validator.GlobalValidator;
import com.schedule.user.entity.User;
import com.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final GlobalValidator globalValidator;
    
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request, Long  userId) {
        User user = globalValidator.findOrException(userRepository,userId);
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
    public List<GetOneScheduleResponse> findAll(String username) {
        List<Schedule> schedules;
        if (username != null) {
            schedules = scheduleRepository.findAllByUserUsernameOrderByCreatedAtDesc(username);
        } else {
            schedules = scheduleRepository.findAllByOrderByCreatedAtDesc();
        }
        return schedules.stream()
                .map(schedule -> new GetOneScheduleResponse(
                        schedule.getId(),
                        schedule.getUser().getUsername(),
                        schedule.getTitle(),
                        schedule.getDescription(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                ))
                .toList();
    }
    @Transactional(readOnly = true)
    public GetOneScheduleResponse findOne(Long scheduleId) {
        Schedule schedule = globalValidator.findOrException(scheduleRepository,scheduleId);

        return new GetOneScheduleResponse(
                schedule.getId(),
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = globalValidator.findOrException(scheduleRepository,scheduleId);
        // 비밀번호 검증
        globalValidator.validatePassword(schedule, request.getPassword());
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
    public void delete(Long scheduleId, DeleteScheduleRequest request) {
        Schedule schedule = globalValidator.findOrException(scheduleRepository,scheduleId);
        globalValidator.validatePassword(schedule, request.getPassword());
        scheduleRepository.deleteById(scheduleId);
    }

}

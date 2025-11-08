package com.schedule.global.validator;

import com.schedule.schedule.entity.Schedule;
import com.schedule.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GlobalValidator {
    private final ScheduleRepository scheduleRepository;

    public GlobalValidator(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule findScheduleOrException(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 글입니다.")
        );
    }
    public void validatePassword(Schedule schedule, String password) {
        if (!Objects.equals(schedule.getPassword(), password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}

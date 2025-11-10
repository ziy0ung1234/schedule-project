package com.schedule.schedule.service;

import com.schedule.schedule.dto.request.UpdateScheduleRequest;
import com.schedule.schedule.entity.Schedule;
import com.schedule.schedule.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//@SpringBootTest
//class ScheduleServiceTest {
//
//    @Autowired
//    private ScheduleService scheduleService;
//    @Autowired
//    private ScheduleRepository scheduleRepository;
//
//    @Test
//    @Transactional
//    void updateSchedule() throws InterruptedException {
//        // given
//        Schedule schedule = new Schedule("공부하기", "JPA 복습", );
//        Schedule saved = scheduleRepository.save(schedule);
//        Long id = saved.getId();
//
//        // modifiedAt 구분을 위해 약간의 시간 차
//        Thread.sleep(1000);
//
//        // when
//        UpdateScheduleRequest request = new UpdateScheduleRequest();
//        request.setTitle("운동하기");
//        request.setPassword("1234");
//        scheduleService.update(id, request);
////        scheduleRepository.flush();
//        // then
//        Schedule updated = scheduleRepository.findById(id).orElseThrow();
//        System.out.println("before: " + saved.getModifiedAt());
//        System.out.println("after: " + updated.getModifiedAt());
//
//        assertThat(updated.getTitle()).isEqualTo("운동하기");
//        assertThat(updated.getModifiedAt()).isAfter(saved.getCreatedAt());
//    }
//}
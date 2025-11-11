package com.schedule.schedule.controller;

import com.schedule.schedule.dto.request.*;
import com.schedule.schedule.dto.response.*;
import com.schedule.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 일정(Schedule) 관련 CRUD API를 제공하는 컨트롤러 클래스입니다.
 * <p>
 * 사용자 인증 정보(Session의 userId)를 바탕으로 일정을 생성, 조회, 수정, 삭제하는 기능을 담당합니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li>일정 생성: {@link #createSchedule(CreateScheduleRequest, HttpServletRequest)}</li>
 *   <li>단일 일정 조회: {@link #getOneSchedule(Long)}</li>
 *   <li>전체 일정 조회: {@link #getAllSchedules()}</li>
 *   <li>일정 수정: {@link #updateSchedule(Long, UpdateScheduleRequest)}</li>
 *   <li>일정 삭제(비밀번호 확인 포함): {@link #deleteSchedule(Long, DeleteScheduleRequest)}</li>
 * </ul>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule (
            @Valid @RequestBody CreateScheduleRequest request,
            HttpServletRequest httpRequest
    ) {
        Long userId = (Long) httpRequest.getSession().getAttribute("userId");
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request, userId));
    }
    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule (@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }
    @GetMapping
    public ResponseEntity<List<GetOneScheduleResponse>> getAllSchedules() {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll());
    }
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request));
    }
    @PostMapping("/{scheduleId}/delete")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, @Valid @RequestBody DeleteScheduleRequest request) {
        scheduleService.delete(scheduleId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

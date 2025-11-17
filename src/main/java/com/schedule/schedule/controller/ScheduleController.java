package com.schedule.schedule.controller;

import com.schedule.schedule.dto.request.*;
import com.schedule.schedule.dto.response.*;
import com.schedule.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 일정(Schedule) 관련 REST 컨트롤러.
 * <p>일정 생성, 조회, 수정, 삭제를 담당한다.</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule (
            @Valid @RequestBody CreateScheduleRequest request,
            @SessionAttribute("userId") Long userId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request, userId));
    }
    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule (@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }
    @GetMapping
    public ResponseEntity<PageScheduleResponse<GetAllScheduleResponse>> getAllSchedules(
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(pageable));
    }
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request,
            @SessionAttribute("userId") Long userId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request, userId));
    }
    @PostMapping("/{scheduleId}/delete")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody DeleteScheduleRequest request,
            @SessionAttribute("userId") Long userId
    ){
        scheduleService.delete(scheduleId, request, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}


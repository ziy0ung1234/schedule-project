package com.schedule.schedule.dto.response;

import com.schedule.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {
    private final Long id;
    private final String username;
    private final String title;
    private final String description;
    private final LocalDateTime modifiedAt;

    public UpdateScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.username = schedule.getUser().getUsername();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.modifiedAt = schedule.getModifiedAt();
}}

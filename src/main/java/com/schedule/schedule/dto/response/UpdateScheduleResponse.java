package com.schedule.schedule.dto.response;

import com.schedule.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {
    private final Long id;
    private final String title;
    private final String username;
    private final LocalDateTime modifiedAt;

    public UpdateScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.username = schedule.getUser().getUsername();
        this.modifiedAt = schedule.getModifiedAt();
    }
}

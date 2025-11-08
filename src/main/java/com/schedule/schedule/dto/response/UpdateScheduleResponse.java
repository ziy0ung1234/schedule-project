package com.schedule.schedule.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {
    private final Long id;
    private final String title;
    private final String username;
    private final LocalDateTime modifiedAt;

    public UpdateScheduleResponse(Long id, String title, String username, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.modifiedAt = modifiedAt;
    }
}

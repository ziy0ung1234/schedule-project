package com.schedule.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.schedule.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({
        "id", "username", "title", "description","countComments", "createdAt", "modifiedAt"
})
public class GetAllScheduleResponse {
    private final Long id;
    private final String username;
    private final String title;
    private final int countComments;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetAllScheduleResponse(Schedule schedule, int countComments) {
        this.id = schedule.getId();
        this.username = schedule.getUser().getUsername();
        this.title = schedule.getTitle();
        this.countComments = countComments;
        this.description = schedule.getDescription();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }
}
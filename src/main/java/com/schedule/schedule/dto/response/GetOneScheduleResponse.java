package com.schedule.schedule.dto.response;

import com.schedule.comment.dto.response.GetOneCommentResponse;
import com.schedule.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetOneScheduleResponse {
    private final Long id;
    private final String username;
    private final String title;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<GetOneCommentResponse> comments;


    public GetOneScheduleResponse(Schedule schedule,List<GetOneCommentResponse> comments) {
        this.id = schedule.getId();
        this.username = schedule.getUser().getUsername();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.comments = comments;
    }
}

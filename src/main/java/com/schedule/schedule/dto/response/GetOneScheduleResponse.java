package com.schedule.schedule.dto.response;

import com.schedule.comment.dto.response.GetOneCommentResponse;
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


    public GetOneScheduleResponse(Long id, String username, String title, String description, LocalDateTime createdAt, LocalDateTime modifiedAt, List<GetOneCommentResponse> comments) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }
}

package com.schedule.comment.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCommentResponse {
    private final Long id;
    private final String username;
    private final String scheduleTitle;
    private final String comment;
    private final LocalDateTime modifiedAt;

    public UpdateCommentResponse(Long id, String username, String scheduleTitle, String comment, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.scheduleTitle = scheduleTitle;
        this.comment = comment;
        this.modifiedAt = modifiedAt;
    }
}

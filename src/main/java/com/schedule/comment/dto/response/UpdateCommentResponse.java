package com.schedule.comment.dto.response;

import com.schedule.comment.entity.Comment;

import java.time.LocalDateTime;

public class UpdateCommentResponse{
    private final Long id;
    private final String username;
    private final String scheduleTitle;
    private final String comment;
    private final LocalDateTime modifiedAt;

    public UpdateCommentResponse(Comment comment) {
        this.id = comment.getId();
        this.username =comment.getUser().getUsername();
        this.scheduleTitle =comment.getSchedule().getTitle();
        this.comment =comment.getContent();
        this.modifiedAt =comment.getModifiedAt();
    }
}


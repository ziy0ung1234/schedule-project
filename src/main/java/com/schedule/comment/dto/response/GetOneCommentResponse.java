package com.schedule.comment.dto.response;

import com.schedule.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneCommentResponse {
    private final Long id;
    private final String username;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetOneCommentResponse(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}

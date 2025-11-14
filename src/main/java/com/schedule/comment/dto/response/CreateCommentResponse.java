package com.schedule.comment.dto.response;

import java.time.LocalDateTime;

public record CreateCommentResponse(Long id, String username, String comment, LocalDateTime createdAt,
                                    LocalDateTime modifiedAt) {
}

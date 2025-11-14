package com.schedule.comment.dto.response;

import java.time.LocalDateTime;

public record UpdateCommentResponse(Long id, String username, String scheduleTitle, String comment,
                                    LocalDateTime modifiedAt) {
}

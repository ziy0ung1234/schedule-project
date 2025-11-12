package com.schedule.schedule.dto.response;

import java.time.LocalDateTime;


public record GetAllScheduleResponse(Long id, String username, String title, String description,
                                     Integer countComment, LocalDateTime createdAt, LocalDateTime modifiedAt) {
}

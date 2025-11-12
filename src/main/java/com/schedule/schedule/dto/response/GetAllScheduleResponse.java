package com.schedule.schedule.dto.response;

import java.time.LocalDateTime;


public record GetAllScheduleResponse(Long id, String username, String title, String description,
                                     LocalDateTime createdAt, LocalDateTime modifiedAt) {
}

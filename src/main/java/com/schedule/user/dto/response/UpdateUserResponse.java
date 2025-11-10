package com.schedule.user.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateUserResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime modifiedAt;

    public UpdateUserResponse(Long id, String username, String email, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.modifiedAt = modifiedAt;
    }
}

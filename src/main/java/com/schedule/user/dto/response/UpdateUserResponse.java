package com.schedule.user.dto.response;

import com.schedule.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateUserResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime modifiedAt;

    public UpdateUserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.modifiedAt = user.getModifiedAt();
    }
}

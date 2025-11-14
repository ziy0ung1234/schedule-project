package com.schedule.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.schedule.comment.dto.response.GetOneCommentResponse;
import com.schedule.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonPropertyOrder({
        "id", "username", "title", "description", "createdAt", "modifiedAt", "comments"
}) // HashMap 구조를 사용해 JSON 객체 생성 -> 순서 보장 X -> 순서 유지 명시
public class GetOneScheduleResponse {
    private final Long id;
    private final String username;
    private final String title;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<GetOneCommentResponse> comments;


    public GetOneScheduleResponse(Schedule schedule,List<GetOneCommentResponse> comments) {
        this.id = schedule.getId();
        this.username = schedule.getUser().getUsername();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.comments = comments;
    }
}

package com.schedule.comment.controller;

import com.schedule.comment.dto.request.*;
import com.schedule.comment.dto.response.*;
import com.schedule.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 댓글 관련 CRUD 기능을 제공하는 REST 컨트롤러입니다.
 * <p>
 * 사용자의 세션 정보(Session의 {@code userId})를 기반으로 댓글을 생성, 조회, 수정, 삭제할 수 있습니다.
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<CreateCommentResponse> createComment (
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest request,
            @SessionAttribute("userId") Long userId
    ) {
        // 글작성자가 아닌 세션으로 로그인중인 현재 유저
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(request,scheduleId, userId));
    }
    @GetMapping("/{commentId}")
    public ResponseEntity<GetOneCommentResponse> getOneComment (
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @SessionAttribute("userId") Long userId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findOne(commentId, userId));
    }
    @GetMapping
    public ResponseEntity<List<GetOneCommentResponse>> getAllComments(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll());
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            @SessionAttribute("userId") Long userId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(commentId, request, userId));
    }
    @PostMapping("/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody DeleteCommenteRequest request,
            @SessionAttribute("userId") Long userId
    ){
        commentService.delete(commentId, request, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

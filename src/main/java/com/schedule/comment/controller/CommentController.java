package com.schedule.comment.controller;

import com.schedule.comment.dto.request.*;
import com.schedule.comment.dto.response.*;
import com.schedule.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * 댓글(Comment) 관련 CRUD API를 제공하는 컨트롤러 클래스입니다.
 * <p>
 * 사용자 인증 정보(Session의 userId)를 바탕으로 댓글을 생성, 조회, 수정, 삭제하는 기능을 담당합니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li>댓글 생성: {@link #createComment(Long,CreateCommentRequest, HttpServletRequest)}</li>
 *   <li>단일 댓글 조회: {@link #getOneComment(Long,Long,HttpServletRequest)}</li>
 *   <li>전체 댓글 조회: {@link #getAllComments(Long,HttpServletRequest)}</li>
 *   <li>댓글 수정: {@link #updateComment(Long, Long,UpdateCommentRequest,HttpServletRequest)}</li>
 *   <li>댓글 삭제(비밀번호 확인 포함): {@link #deleteComment(Long,Long, DeleteCommenteRequest,HttpServletRequest)}</li>
 * </ul>
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
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(request,scheduleId, httpRequest));
    }
    @GetMapping("/{commentId}")
    public ResponseEntity<GetOneCommentResponse> getOneComment (
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            HttpServletRequest httpRequest
    ) throws AccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findOne(commentId,scheduleId, httpRequest));
    }
    @GetMapping
    public ResponseEntity<List<GetOneCommentResponse>> getAllComments(
            @PathVariable Long scheduleId,
            HttpServletRequest httpRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(httpRequest));
    }
    @PatchMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            HttpServletRequest httpRequest
    ) throws AccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(scheduleId,commentId, request, httpRequest));
    }
    @PostMapping("/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody DeleteCommenteRequest request,
            HttpServletRequest httpRequest
    ) throws AccessDeniedException {
        commentService.delete(scheduleId, commentId, request, httpRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

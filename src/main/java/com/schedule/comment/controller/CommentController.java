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

import java.util.List;
/**
 * 댓글 관련 CRUD 기능을 제공하는 REST 컨트롤러입니다.
 * <p>
 * 사용자의 세션 정보(Session의 {@code userId})를 기반으로 댓글을 생성, 조회, 수정, 삭제할 수 있습니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li><b>댓글 생성</b>: 일정에 새 댓글을 추가합니다. (<em>POST /schedules/{scheduleId}/comments</em>)</li>
 *   <li><b>단일 댓글 조회</b>: 댓글 ID를 기준으로 특정 댓글을 조회합니다. (<em>GET /schedules/{scheduleId}/comments/{commentId}</em>)</li>
 *   <li><b>전체 댓글 조회</b>: 특정 일정에 달린 모든 댓글을 조회합니다. (<em>GET /schedules/{scheduleId}/comments</em>)</li>
 *   <li><b>댓글 수정</b>: 댓글 내용을 수정합니다. (<em>PATCH /schedules/{scheduleId}/comments/{commentId}</em>)</li>
 *   <li><b>댓글 삭제</b>: 비밀번호 확인 후 댓글을 삭제합니다. (<em>POST /schedules/{scheduleId}/comments/{commentId}/delete</em>)</li>
 * </ul>
 *
 * <h2>세션 인증</h2>
 * <p>
 * 모든 요청은 세션에 저장된 {@code userId}를 통해 인증된 사용자를 식별합니다.
 * {@link #sessionUserId(HttpServletRequest)} 메서드를 사용하여 세션에서 현재 사용자 ID를 추출합니다.
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
            HttpServletRequest httpRequest
    ) {
        // 글작성자가 아닌 세션으로 로그인중인 현재 유저
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(request,scheduleId, sessionUserId(httpRequest)));
    }
    @GetMapping("/{commentId}")
    public ResponseEntity<GetOneCommentResponse> getOneComment (
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            HttpServletRequest httpRequest ){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findOne(commentId, sessionUserId(httpRequest)));
    }
    @GetMapping
    public ResponseEntity<List<GetOneCommentResponse>> getAllComments(
            @PathVariable Long scheduleId,
            HttpServletRequest httpRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll());
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            HttpServletRequest httpRequest ){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(commentId, request, sessionUserId(httpRequest)));
    }
    @PostMapping("/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody DeleteCommenteRequest request,
            HttpServletRequest httpRequest ){
        commentService.delete(commentId, request, sessionUserId(httpRequest));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //------------공용 메서드--------------
    public Long sessionUserId(HttpServletRequest request){
        return (Long) request.getSession().getAttribute("userId");
    }
}

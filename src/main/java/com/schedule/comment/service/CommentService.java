package com.schedule.comment.service;

import com.schedule.comment.dto.request.*;
import com.schedule.comment.dto.response.*;
import com.schedule.comment.entity.Comment;
import com.schedule.comment.repository.CommentRepository;
import com.schedule.global.validator.GlobalValidator;
import com.schedule.schedule.entity.Schedule;
import com.schedule.schedule.repository.ScheduleRepository;
import com.schedule.user.entity.User;
import com.schedule.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * 댓글(Comment) 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * <p>
 * 컨트롤러 계층과 리포지토리 계층을 연결하며,
 * 트랜잭션 단위로 댓글의 생성, 조회, 수정, 삭제를 담당합니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li>댓글 생성: {@link #save(CreateCommentRequest, Long,HttpServletRequest)}</li>
 *   <li>전체 댓글 조회: {@link #findAll(HttpServletRequest)}</li>
 *   <li>단일 댓글 조회: {@link #findOne(Long,Long,HttpServletRequest)}</li>
 *   <li>댓글 수정: {@link #update(Long,Long, UpdateCommentRequest,HttpServletRequest)}</li>
 *   <li>댓글 삭제: {@link #delete(Long,Long, DeleteCommenteRequest,HttpServletRequest)}</li>
 * </ul>
 *
 * <h2>트랜잭션 정책</h2>
 * <ul>
 *   <li>쓰기 작업(save, update, delete)은 {@code @Transactional}로 관리</li>
 *   <li>조회 작업(findAll, findOne)은 {@code @Transactional(readOnly = true)}로 성능 최적화</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final GlobalValidator globalValidator;
    
    @Transactional
    public CreateCommentResponse save(CreateCommentRequest request, Long schduleId, HttpServletRequest httpRequest) {
        Schedule schedule = globalValidator.findOrException(scheduleRepository, schduleId);
        // 글작성자가 아닌 세션으로 로그인중인 현재 유저
        HttpSession session = httpRequest.getSession(false);
        Long userId = (Long) session.getAttribute("userId");
        User user = globalValidator.findOrException(userRepository, userId);
        Comment comment = new Comment(
                request.getContent(),
                user,
                schedule
        );
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getUser().getUsername(),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetOneCommentResponse> findAll(HttpServletRequest httpRequest) {
       List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc();
        return comments.stream()
                .map(GetOneCommentResponse::new)
                .toList();
    }
    @Transactional(readOnly = true)
    public GetOneCommentResponse findOne(Long commentId, Long schduleId,HttpServletRequest httpRequest) throws AccessDeniedException {
        Comment comment = globalValidator.findOrException(commentRepository,commentId);
        Long userId = (Long) httpRequest.getSession().getAttribute("userId");
        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("본인이 작성한 댓글만 열람할 수 있습니다.");
        }
        return new GetOneCommentResponse(comment);
    }

    @Transactional
    public UpdateCommentResponse update(Long scheduleId, Long commentId, UpdateCommentRequest request,HttpServletRequest httpRequest) throws AccessDeniedException {
        Comment comment = globalValidator.findOrException(commentRepository,commentId);
        Long userId = (Long) httpRequest.getSession().getAttribute("userId");
        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }
        // 비밀번호 검증
        globalValidator.matchPassword(comment, request.getPassword());
        // 선택적 수정
        if (request.getContent() != null && !request.getContent().isBlank()) {
            comment.updateContent(request.getContent());
        }
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            comment.getUser().updateUsername(request.getUsername());
        }
        commentRepository.flush();
        return new UpdateCommentResponse(
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getSchedule().getTitle(),
                comment.getContent(),
                comment.getModifiedAt()
        );
    }
    @Transactional
    public void delete(Long scheduleId, Long commentId, DeleteCommenteRequest request, HttpServletRequest httpRequest) throws AccessDeniedException {
        Comment comment = globalValidator.findOrException(commentRepository, commentId);
        Long userId = (Long) httpRequest.getSession().getAttribute("userId");
        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }
        globalValidator.matchPassword(comment, request.getPassword());

        commentRepository.deleteById(commentId);
    }

}

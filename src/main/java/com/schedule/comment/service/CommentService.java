package com.schedule.comment.service;

import com.schedule.comment.dto.request.*;
import com.schedule.comment.dto.response.*;
import com.schedule.comment.entity.Comment;
import com.schedule.comment.repository.CommentRepository;
import com.schedule.global.config.PasswordEncoder;
import com.schedule.global.exception.CustomException;
import com.schedule.global.exception.ErrorMessage;
import com.schedule.global.validator.CheckSessionUser;
import com.schedule.schedule.entity.Schedule;
import com.schedule.schedule.repository.ScheduleRepository;
import com.schedule.user.entity.User;
import com.schedule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 *   <li>댓글 생성: {@link #save(CreateCommentRequest, Long, Long)}</li>
 *   <li>전체 댓글 조회: {@link #findAll()}</li>
 *   <li>단일 댓글 조회: {@link #findOne(Long,Long)}</li>
 *   <li>댓글 수정: {@link #update(Long, UpdateCommentRequest,Long)}</li>
 *   <li>댓글 삭제: {@link #delete(Long, DeleteCommenteRequest,Long)}</li>
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
    private final CheckSessionUser checkSessionUser;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public CreateCommentResponse save(CreateCommentRequest request, Long schduleId, Long userId) {
        Schedule schedule = scheduleRepository.findOrException( schduleId);
        User user = userRepository.findOrException(userId);
        Comment comment = new Comment(
                request.getContent(),
                user,
                schedule
        );
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(savedComment); // DTO에 엔티티의존은 필드 개수가 4개이상일때
    }

    @Transactional(readOnly = true)
    public List<GetOneCommentResponse> findAll() {
       List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc();
        return comments.stream()
                .map(GetOneCommentResponse::new)
                .toList();
    }
    @Transactional(readOnly = true)
    public GetOneCommentResponse findOne(Long commentId,Long userId) {
        Comment comment = commentRepository.findOrException(commentId);
        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorMessage.FORBIDDEN);
        }
        return new GetOneCommentResponse(comment);
    }

    @Transactional
    public UpdateCommentResponse update(Long commentId, UpdateCommentRequest request,Long userId){
        Comment comment = commentRepository.findOrException(commentId);
        checkSessionUser.forbiddenUserHandler(comment,userId);
        // 비밀번호 검증
        matchPassword(comment, request.getPassword());
        // 선택적 수정
        if (request.getContent() != null && !request.getContent().isBlank()) {
            comment.updateContent(request.getContent());
        }
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            comment.getUser().updateUsername(request.getUsername());
        }
        commentRepository.flush();
        return new UpdateCommentResponse(comment);
    }
    @Transactional
    public void delete(Long commentId, DeleteCommenteRequest request, Long userId) {
        Comment comment = commentRepository.findOrException(commentId);
        checkSessionUser.forbiddenUserHandler(comment,userId);
        matchPassword(comment, request.getPassword());
        commentRepository.deleteById(commentId);
    }
    //----------공용 메서드---------------
    public void matchPassword(Comment comment, String password) {
        boolean isMatched = passwordEncoder.matches(password, comment.getPassword());
        if (!isMatched) {
            throw new CustomException(ErrorMessage.NOT_MATCHED_PASSWORD);
        }
    }

}

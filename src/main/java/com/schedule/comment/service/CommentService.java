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
 * 댓글 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * <p>
 * 컨트롤러 계층과 리포지토리 계층 사이에서 동작하며,
 * 댓글의 생성, 조회, 수정, 삭제를 트랜잭션 단위로 관리합니다.
 * <br>
 * 또한, 댓글 작성자(Session 사용자) 및 비밀번호 검증을 통해
 * 접근 제어를 수행합니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *   <li><b>댓글 생성</b>: {@link #save(CreateCommentRequest, Long, Long)} – 특정 일정에 댓글 추가</li>
 *   <li><b>전체 댓글 조회</b>: {@link #findAll()} – 최신순 정렬</li>
 *   <li><b>단일 댓글 조회</b>: {@link #findOne(Long, Long)} – 세션 사용자 검증 포함</li>
 *   <li><b>댓글 수정</b>: {@link #update(Long, UpdateCommentRequest, Long)} – 비밀번호 및 세션 사용자 검증 후 수정</li>
 *   <li><b>댓글 삭제</b>: {@link #delete(Long, DeleteCommenteRequest, Long)} – 비밀번호 및 세션 사용자 검증 후 삭제</li>
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
        if (request.getContent() != null && !request.getContent().isBlank()) {
            comment.updateContent(request.getContent());
        }
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

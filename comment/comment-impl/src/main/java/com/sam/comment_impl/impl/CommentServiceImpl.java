package com.sam.comment_impl.impl;

import com.sam.comment_api.dto.request.CommentCreationRequest;
import com.sam.comment_api.dto.request.CommentUpdateRequest;
import com.sam.comment_api.dto.response.CommentResponse;
import com.sam.comment_api.service.CommentService;
import com.sam.blog_core.dto.response.PageResponse;
import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.comment_impl.entity.Comment;
import com.sam.comment_impl.mapper.CommentMapper;
import com.sam.comment_impl.repository.CommentRepository;
import com.sam.post_api.service.PostService;
import com.sam.blog_user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {
    CommentRepository repository;
    CommentMapper mapper;
    UserService userService;
    PostService postService;

    @Override
    public CommentResponse createPost(Long commenterId, CommentCreationRequest request) {
        Comment comment = mapper.createCommentFromRequest(request);
        comment.setUserId(commenterId);

        return mapper.toCommentResponse(repository.save(comment));
    }

    @Override
    public PageResponse<CommentResponse> getAllByPostId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Comment> comments = repository.findByPostId(postId, pageable);
        List<CommentResponse> commentResponses = comments.getContent().stream().map(mapper::toCommentResponse).toList();
        return PageResponse.of(commentResponses, comments);
    }

    @Override
    public CommentResponse updateComment(Long id, Long commenterId, CommentUpdateRequest request) {
        Comment comment = findByIdAndCommenterId(id, commenterId);
        comment = mapper.updateCommentFromRequest(request, comment);
        return mapper.toCommentResponse(repository.save(comment));
    }

    @Override
    public void deleteComment(Long id, Long commenterId) {
        Comment comment = findByIdAndCommenterId(id, commenterId);
        repository.delete(comment);
    }

    private Comment findCommentEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public Comment findByIdAndCommenterId(Long commentId, Long commenterId) {
        return repository.findByIdAndUserId(commentId, commenterId).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }
}

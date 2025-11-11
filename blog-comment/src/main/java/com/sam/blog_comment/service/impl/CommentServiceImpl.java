package com.sam.blog_comment.service.impl;

import com.sam.blog_comment.dto.request.CommentCreationRequest;
import com.sam.blog_comment.dto.request.CommentUpdateRequest;
import com.sam.blog_comment.dto.response.CommentResponse;
import com.sam.blog_comment.entity.Comment;
import com.sam.blog_comment.mapper.CommentMapper;
import com.sam.blog_comment.repository.CommentRepository;
import com.sam.blog_comment.service.CommentService;
import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.blog_post.dto.response.PageResponse;
import com.sam.blog_post.entity.Post;
import com.sam.blog_post.service.PostService;
import com.sam.blog_user.entity.User;
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
    public CommentResponse create(String username, CommentCreationRequest request) {
        User user = userService.findUserByUsername(username);
        Post post = postService.findById(request.getPostId());

        Comment comment = mapper.createCommentFromRequest(request);
        comment.setCommenter(user);
        comment.setPost(post);

        return mapper.toCommentResponse(repository.save(comment));
    }

    @Override
    public PageResponse<CommentResponse> getAllByPostId(Long postId, int page, int size) {
        Post post = postService.findById(postId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Comment> comments = repository.findByPost(post, pageable);
        List<CommentResponse> commentResponses = comments.getContent().stream().map(mapper::toCommentResponse).toList();
        return PageResponse.of(commentResponses, comments);
    }

    @Override
    public CommentResponse update(String username, Long commentId, CommentUpdateRequest request) {
        User commenter = userService.findUserByUsername(username);
        Comment comment = findByIdAndCommenter(commentId, commenter);
        comment = mapper.updateCommentFromRequest(request, comment);
        return mapper.toCommentResponse(repository.save(comment));
    }

    @Override
    public void delete(String username, Long commentId) {
        User commenter = userService.findUserByUsername(username);
        Comment comment = findByIdAndCommenter(commentId, commenter);
        repository.delete(comment);
    }

    @Override
    public Comment findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));
    }

    @Override
    public Comment findByIdAndCommenter(Long id, User user) {
        return repository.findByIdAndCommenter(id, user).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }
}

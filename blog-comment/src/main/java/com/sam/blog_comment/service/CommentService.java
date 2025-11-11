package com.sam.blog_comment.service;

import com.sam.blog_comment.dto.request.CommentCreationRequest;
import com.sam.blog_comment.dto.request.CommentUpdateRequest;
import com.sam.blog_comment.dto.response.CommentResponse;
import com.sam.blog_comment.entity.Comment;
import com.sam.blog_post.dto.response.PageResponse;
import com.sam.blog_user.entity.User;

public interface CommentService {
    CommentResponse create(String username, CommentCreationRequest request);

    PageResponse<CommentResponse> getAllByPostId(Long postId, int page, int size);

    CommentResponse update(String username, Long commentId, CommentUpdateRequest request);

    void delete(String username, Long commentId);

    Comment findById(Long id);

    Comment findByIdAndCommenter(Long id, User user);
}

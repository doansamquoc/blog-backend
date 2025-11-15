package com.sam.comment_api.service;

import com.sam.blog_core.dto.response.PageResponse;
import com.sam.comment_api.dto.CommentDTO;
import com.sam.comment_api.dto.request.CommentCreationRequest;
import com.sam.comment_api.dto.request.CommentUpdateRequest;
import com.sam.comment_api.dto.response.CommentResponse;
import com.sam.blog_user.entity.User;

public interface CommentService {
    CommentResponse createPost(Long commenterId, CommentCreationRequest request);

    PageResponse<CommentResponse> getAllByPostId(Long postId, int page, int size);

    CommentResponse updateComment(Long id, Long commenterId, CommentUpdateRequest request);

    void deleteComment(Long id, Long commenterId);
}

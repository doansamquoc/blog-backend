package com.sam.post_api.service;

import com.sam.blog_core.dto.response.PageResponse;
import com.sam.post_api.dto.PostDTO;
import com.sam.post_api.dto.reponse.PostResponse;
import com.sam.post_api.dto.request.PostCreationRequest;
import com.sam.post_api.dto.request.PostUpdateRequest;

public interface PostService {
    PostResponse create(Long userId, PostCreationRequest request);

    PageResponse<PostResponse> getAll(int page, int size);

    PostResponse getPostById(Long id);

    PostDTO findPostDTOById(Long id);

    void deleteByIdAndAuthorId(Long postId, Long authorId);

    PostResponse updateByIdAndAuthorId(Long postId, Long authorId, PostUpdateRequest request);
}

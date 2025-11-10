package com.sam.blog_post.service;

import com.sam.blog_post.dto.request.PostCreationRequest;
import com.sam.blog_post.dto.request.PostUpdateRequest;
import com.sam.blog_post.dto.response.PageResponse;
import com.sam.blog_post.dto.response.PostResponse;

public interface PostService {
    PostResponse create(String username, PostCreationRequest request);

    PageResponse<PostResponse> getAll(int page, int size);

    PostResponse getById(Long id);

    void deleteById(String username, Long id);


    PostResponse updateByIdAndUsername(Long id, String username, PostUpdateRequest request);
}

package com.sam.like_impl.service;

import com.sam.blog_core.dto.response.PageResponse;
import com.sam.like_impl.dto.request.LikeCreationRequest;
import com.sam.like_impl.dto.response.LikeResponse;
import com.sam.like_api.dto.LikeDTO;

import java.util.List;
import java.util.Map;

public interface LikeService {
    LikeResponse create(Long userId, LikeCreationRequest request);

    void delete(Long userId, Long postId);

    PageResponse<LikeResponse> getAll(Long postId, int page, int size);

    LikeDTO findLikeDTO(Long userId, Long postId);

    Map<Long, Long> countByPostIds(List<Long> postIds);
}

package com.sam.post_api.controller;

import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import com.sam.blog_core.dto.response.PageResponse;
import com.sam.blog_core.utils.JWTUtils;
import com.sam.post_api.dto.reponse.PostResponse;
import com.sam.post_api.dto.request.PostCreationRequest;
import com.sam.post_api.dto.request.PostUpdateRequest;
import com.sam.post_api.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService service;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody PostCreationRequest request
    ) {
        Long userId = JWTUtils.getLongClaim(jwt, "id");
        PostResponse response = service.create(userId, request);
        return ApiResponseFactory.success(response, "Post created successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PostResponse>>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        PageResponse<PostResponse> response = service.getAll(page, size);
        return ApiResponseFactory.success(response, "Get posts successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteById(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") Long postId
    ) {
        Long authorId = JWTUtils.getLongClaim(jwt, "id");
        service.deleteByIdAndAuthorId(postId, authorId);
        return ApiResponseFactory.success("Post has been deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> update(
            @PathVariable("id") Long postId,
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody PostUpdateRequest request
    ) {
        Long authorId = JWTUtils.getLongClaim(jwt, "id");
        PostResponse response = service.updateByIdAndAuthorId(postId, authorId, request);
        return ApiResponseFactory.success(response, "Post updated successfully");
    }
}

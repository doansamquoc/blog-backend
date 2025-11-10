package com.sam.blog_post.controller;

import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import com.sam.blog_post.dto.request.PostCreationRequest;
import com.sam.blog_post.dto.request.PostUpdateRequest;
import com.sam.blog_post.dto.response.PageResponse;
import com.sam.blog_post.dto.response.PostResponse;
import com.sam.blog_post.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<PostResponse>> create(@AuthenticationPrincipal Jwt jwt, @RequestBody PostCreationRequest request) {
        PostResponse response = service.create(jwt.getSubject(), request);
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
            @PathVariable("id") Long id
    ) {
        service.deleteById(jwt.getSubject(), id);
        return ApiResponseFactory.success("Post has been deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> update(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody PostUpdateRequest request
    ) {
        PostResponse response = service.updateByIdAndUsername(id, jwt.getSubject(), request);
        return ApiResponseFactory.success(response, "Post updated successfully");
    }
}

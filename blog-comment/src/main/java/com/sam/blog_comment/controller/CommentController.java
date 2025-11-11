package com.sam.blog_comment.controller;

import com.sam.blog_comment.dto.request.CommentCreationRequest;
import com.sam.blog_comment.dto.request.CommentUpdateRequest;
import com.sam.blog_comment.dto.response.CommentResponse;
import com.sam.blog_comment.service.CommentService;
import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import com.sam.blog_post.dto.response.PageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentController {
    CommentService service;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CommentResponse>>> getAllByPost(
            @RequestParam(name = "postId") Long postId,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size
    ) {
        PageResponse<CommentResponse> response = service.getAllByPostId(postId, page, size);
        return ApiResponseFactory.success(response, "Get comments successfully");
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid CommentCreationRequest request
    ) {
        CommentResponse response = service.create(jwt.getSubject(), request);
        return ApiResponseFactory.success(response, "Comment has been created successfully");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> update(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody @Valid CommentUpdateRequest request
    ) {
        CommentResponse response = service.update(jwt.getSubject(), commentId, request);
        return ApiResponseFactory.success(response, "Comment has been updated successfully");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<String>> delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "commentId") Long commentId
    ) {
        service.delete(jwt.getSubject(), commentId);
        return ApiResponseFactory.success("Comment has been deleted successfully");
    }
}

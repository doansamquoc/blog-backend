package com.sam.comment_api.controller;

import com.sam.blog_core.dto.response.PageResponse;
import com.sam.blog_core.utils.JWTUtils;
import com.sam.comment_api.dto.request.CommentCreationRequest;
import com.sam.comment_api.dto.request.CommentUpdateRequest;
import com.sam.comment_api.dto.response.CommentResponse;
import com.sam.comment_api.service.CommentService;
import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
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
        Long commenterId = JWTUtils.getLongClaim(jwt, "id");
        CommentResponse response = service.createPost(commenterId, request);
        return ApiResponseFactory.success(response, "Comment has been created successfully");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> update(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "commentId") Long id,
            @RequestBody @Valid CommentUpdateRequest request
    ) {
        Long commenterId = JWTUtils.getLongClaim(jwt, "id");
        CommentResponse response = service.updateComment(id, commenterId, request);
        return ApiResponseFactory.success(response, "Comment has been updated successfully");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<String>> delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "commentId") Long id
    ) {
        Long commenterId = JWTUtils.getLongClaim(jwt, "id");
        service.deleteComment(id, commenterId);
        return ApiResponseFactory.success("Comment has been deleted successfully");
    }
}

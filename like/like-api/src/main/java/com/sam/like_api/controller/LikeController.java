package com.sam.like_impl.controller;

import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import com.sam.blog_core.dto.response.PageResponse;
import com.sam.blog_core.utils.JWTUtils;
import com.sam.like_impl.dto.request.LikeCreationRequest;
import com.sam.like_impl.dto.response.LikeResponse;
import com.sam.like_impl.service.LikeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LikeController {
    LikeService service;

    @PostMapping
    public ResponseEntity<ApiResponse<LikeResponse>> create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid LikeCreationRequest request
    ) {
        Long userId = JWTUtils.getLongClaim(jwt, "id");
        LikeResponse likeResponse = service.create(userId, request);
        return ApiResponseFactory.success(likeResponse, "Liked successfully");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<String>> delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable(name = "postId") Long postId
    ) {
        Long userId = JWTUtils.getLongClaim(jwt, "id");
        service.delete(userId, postId);
        return ApiResponseFactory.success("Unliked successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<LikeResponse>>> getAllByPost(
            @RequestParam("postId") Long postId,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size
    ) {
        PageResponse<LikeResponse> response = service.getAll(postId, page, size);
        return ApiResponseFactory.success(response, "Get like(s) successfully");
    }
}

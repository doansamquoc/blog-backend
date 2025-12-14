package com.sam.blog_user.controller;

import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import com.sam.blog_user.dto.request.CheckEmailAddressRequest;
import com.sam.blog_user.dto.request.CheckUsernameRequest;
import com.sam.blog_user.dto.request.UserDeleteRequest;
import com.sam.blog_user.dto.request.UserUpdateRequest;
import com.sam.blog_user.dto.response.UserResponse;
import com.sam.blog_user.service.UserService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService service;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me(@AuthenticationPrincipal Jwt jwt) {
        UserResponse userResponse = service.getUserByUsername(jwt.getSubject());
        return ApiResponseFactory.success(userResponse, "Get current user successfully");
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UserUpdateRequest request
    ) {
        UserResponse userResponse = service.update(jwt.getSubject(), request);
        return ApiResponseFactory.success(userResponse, "User information updated successfully");
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> delete(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UserDeleteRequest request
    ) {
        service.delete(jwt.getSubject(), request);
        return ApiResponseFactory.success("User deleted successfully");
    }

    @PostMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestBody @Valid CheckUsernameRequest request) {
        HttpStatus status = service.checkUsername(request);
        return ResponseEntity.status(status).build();
    }

    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestBody CheckEmailAddressRequest request) {
        HttpStatus status = service.checkEmail(request);
        return ResponseEntity.status(status).build();
    }
}

package com.sam.blog_auth.controller;

import com.sam.blog_auth.dto.request.SignInRequest;
import com.sam.blog_auth.dto.request.SignUpRequest;
import com.sam.blog_auth.dto.response.AuthResponse;
import com.sam.blog_auth.service.AuthService;
import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<AuthResponse>> signUp(
            @RequestBody @Valid SignUpRequest r,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.signUp(r, request, response);
        return ApiResponseFactory.created(authResponse, "Sign up successfully");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<AuthResponse>> signIn(
            @RequestBody SignInRequest request,
            HttpServletRequest servletRequest,
            HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.signIn(request, servletRequest, response);
        return ApiResponseFactory.success(authResponse, "Sign in successfully");
    }

    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse<Object>> signOut(HttpServletRequest request, HttpServletResponse response) {
        authService.signOut(request, response);
        return ApiResponseFactory.success("Sign out successfully");
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.refresh(request, response);
        return ApiResponseFactory.success(authResponse, "Request new access token successfully");
    }
}

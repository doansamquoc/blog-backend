package com.sam.blog_auth.controller;

import com.sam.blog_auth.dto.request.PasswordResetRequest;
import com.sam.blog_auth.dto.request.RequestRestPasswordRequest;
import com.sam.blog_auth.dto.request.SignInRequest;
import com.sam.blog_auth.dto.request.SignUpRequest;
import com.sam.blog_auth.dto.response.AuthResponse;
import com.sam.blog_auth.service.AuthService;
import com.sam.blog_auth.service.PasswordResetTokenService;
import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import com.sam.blog_auth.dto.request.PasswordUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthController {
    final AuthService authService;
    final PasswordResetTokenService passwordResetTokenService;

    @Value("${frontend.url}")
    String frontendUrl;

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

    @PostMapping("/password-reset")
    public ResponseEntity<ApiResponse<Object>> requestPasswordReset(
            @RequestBody RequestRestPasswordRequest request,
            HttpServletRequest servletRequest
    ) {
        authService.requestResetPassword(request, servletRequest);
        return ApiResponseFactory.success("We have sent an email to your email address. Please check and follow verify step to reset password.");
    }

    @GetMapping("/password-reset/verify")
    public void verifyPasswordResetToken(@RequestParam("token") String token, HttpServletResponse servletResponse) throws IOException {
        try {
            passwordResetTokenService.verifyPasswordResetToken(token);
            servletResponse.sendRedirect(frontendUrl + "/password-reset?token=" + token);
        } catch (IOException exception) {
            servletResponse.sendRedirect(frontendUrl + "/password-reset/invalid");
        }
    }

    @PostMapping("/password-reset/confirm")
    public ResponseEntity<ApiResponse<Object>> resetPassword(
            @RequestParam("token") String token,
            @RequestBody PasswordResetRequest request,
            HttpServletRequest servletRequest
    ) {
        authService.resetPassword(token, request, servletRequest);
        return ApiResponseFactory.success("Your password has been changed.");
    }

    @PostMapping("/password-update")
    public ResponseEntity<ApiResponse<String>> updatePassword(
            @RequestBody PasswordUpdateRequest request,
            HttpServletRequest servletRequest
    ) {
        authService.updatePassword(request, servletRequest);
        return ApiResponseFactory.success("Your password has been changed.");
    }

}

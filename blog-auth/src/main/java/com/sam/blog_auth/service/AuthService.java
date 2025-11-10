package com.sam.blog_auth.service;

import com.sam.blog_auth.dto.request.PasswordResetRequest;
import com.sam.blog_auth.dto.request.RequestRestPasswordRequest;
import com.sam.blog_auth.dto.request.SignInRequest;
import com.sam.blog_auth.dto.request.SignUpRequest;
import com.sam.blog_auth.dto.response.AuthResponse;
import com.sam.blog_auth.dto.request.PasswordUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    AuthResponse signUp(SignUpRequest r, HttpServletRequest request, HttpServletResponse response);

    AuthResponse signIn(SignInRequest r, HttpServletRequest request, HttpServletResponse response);

    void signOut(HttpServletRequest request, HttpServletResponse response);

    AuthResponse refresh(HttpServletRequest request, HttpServletResponse response);

    void updatePassword(String username, PasswordUpdateRequest r, HttpServletRequest request);

    void requestResetPassword(RequestRestPasswordRequest r, HttpServletRequest request);

    void resetPassword(String token, PasswordResetRequest request, HttpServletRequest servletRequest);
}

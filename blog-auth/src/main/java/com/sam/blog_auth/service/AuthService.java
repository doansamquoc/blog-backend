package com.sam.blog_auth.service;

import com.sam.blog_auth.dto.request.SignInRequest;
import com.sam.blog_auth.dto.request.SignUpRequest;
import com.sam.blog_auth.dto.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    AuthResponse signUp(SignUpRequest request,  HttpServletResponse response);
    AuthResponse signIn(SignInRequest request, HttpServletRequest servletRequest, HttpServletResponse response);
}

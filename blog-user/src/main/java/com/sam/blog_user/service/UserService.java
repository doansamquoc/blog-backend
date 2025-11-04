package com.sam.blog_user.service;

import com.sam.blog_user.dto.request.UserUpdatePasswordRequest;
import com.sam.blog_user.dto.request.UserUpdateRequest;
import com.sam.blog_user.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    UserResponse me();
    UserResponse updateById(Long id, UserUpdateRequest r, HttpServletRequest request);

    void updatePassword(UserUpdatePasswordRequest r, HttpServletRequest request);
}

package com.sam.blog_user.service;

import com.sam.blog_user.dto.request.UserUpdateRequest;
import com.sam.blog_user.dto.response.UserResponse;
import com.sam.blog_user.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    UserResponse me();

    User authenticatedUser();

    UserResponse updateById(Long id, UserUpdateRequest r, HttpServletRequest request);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    User save(User user);

    boolean existsByUsername(String username);

    boolean existsByEmailAddress(String email);
}

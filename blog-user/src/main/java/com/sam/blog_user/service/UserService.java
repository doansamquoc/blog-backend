package com.sam.blog_user.service;

import com.sam.blog_user.dto.request.UserDeleteRequest;
import com.sam.blog_user.dto.request.UserUpdateRequest;
import com.sam.blog_user.dto.response.UserResponse;
import com.sam.blog_user.entity.User;

public interface UserService {
    UserResponse me(String username);

    UserResponse update(String username, UserUpdateRequest request);

    void delete(String username, UserDeleteRequest request);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    void save(User user);

    boolean existsByUsername(String username);

    boolean existsByEmailAddress(String email);
}

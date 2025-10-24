package com.sam.blog_user.service;

import com.sam.blog_user.dto.request.UserCreationRequest;
import com.sam.blog_user.dto.response.UserResponse;

public interface UserService {
    public UserResponse createUser(UserCreationRequest request);
}

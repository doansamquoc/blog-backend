package com.sam.blog_user.controller;

import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import com.sam.blog_user.dto.response.UserResponse;
import com.sam.blog_user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileController {
    UserService service;

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<UserResponse>> profile(@PathVariable(name = "username") String username) {
        UserResponse userResponse = service.getUserByUsername(username);
        return ApiResponseFactory.success(userResponse, "Get user successfully");
    }
}

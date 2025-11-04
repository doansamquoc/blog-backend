package com.sam.blog_user.controller;

import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import com.sam.blog_user.dto.request.UserUpdatePasswordRequest;
import com.sam.blog_user.dto.response.UserResponse;
import com.sam.blog_user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me() {
        UserResponse userResponse = userService.me();
        return ApiResponseFactory.success(userResponse, "Get current user successfully");
    }

    @PostMapping("/update-password")
    public ResponseEntity<ApiResponse<Object>> updatePasswordById(@RequestBody UserUpdatePasswordRequest r, HttpServletRequest request) {
        userService.updatePassword(r, request);
        return ApiResponseFactory.success("Your password has been changed");
    }
}

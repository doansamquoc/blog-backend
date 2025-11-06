package com.sam.blog_auth.controller;

import com.sam.blog_auth.dto.request.PasswordResetRequest;
import com.sam.blog_auth.dto.request.RequestRestPasswordRequest;
import com.sam.blog_auth.service.PasswordResetTokenService;
import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/password-reset")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetController {
    PasswordResetTokenService service;

//    @PostMapping("/request-password-reset")
//    public ResponseEntity<ApiResponse<Object>> requestResetPassword(
//            @RequestBody RequestRestPasswordRequest r,
//            HttpServletRequest request
//    ) {
//        service.requestResetPassword(r, request);
//        return ApiResponseFactory.success("We have sent an email to your email address. Please check your email box to reset your password.");
//    }
//
//    @GetMapping
//    public void verifyPasswordResetToken(@RequestParam("token") String token, HttpServletResponse servletResponse) throws IOException {
//        service.verifyPasswordResetToken(token);
//        // Redirect to font-end
//        servletResponse.sendRedirect("http://localhost:8080/password-reset?token=" + token);
//    }
//
//    @PostMapping
//    public ResponseEntity<ApiResponse<Object>> passwordReset(
//            @RequestParam("token") String token,
//            @RequestBody PasswordResetRequest request,
//            HttpServletRequest servletRequest
//    ) {
//        service.resetPassword(token, request, servletRequest);
//        return ApiResponseFactory.success("Your password has been changed!");
//    }
}

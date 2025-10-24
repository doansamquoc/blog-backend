package com.sam.blog_mailer.controller;

import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import com.sam.blog_mailer.dto.request.MailRequest;
import com.sam.blog_mailer.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailController {
    MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<Object>> sendTextMail(@RequestBody MailRequest r, HttpServletRequest request) {
        mailService.sendTextMail(r, request);
        return ApiResponseFactory.success("Send mail successfully");
    }
}

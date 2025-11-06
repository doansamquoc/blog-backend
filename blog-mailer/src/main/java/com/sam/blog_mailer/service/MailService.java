package com.sam.blog_mailer.service;

import com.sam.blog_mailer.dto.request.MailRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface MailService {
    void sendPasswordChangedMail(MailRequest r, HttpServletRequest request);

    void sendPasswordResetMail(MailRequest r, HttpServletRequest request);

    void sendTextMail(MailRequest r, HttpServletRequest request);
}

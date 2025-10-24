package com.sam.blog_mailer.service.impl;

import com.sam.blog_core.enums.ErrorCode;
import com.sam.blog_core.exception.BusinessException;
import com.sam.blog_mailer.dto.request.MailRequest;
import com.sam.blog_mailer.mapper.SimpleMailMessageMapper;
import com.sam.blog_mailer.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailServiceImpl implements MailService {
    JavaMailSender mailSender;
    SimpleMailMessageMapper mapper;

    @Override
    public void sendTextMail(MailRequest r, HttpServletRequest request) {
        try {
            String clientIp = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");

            String fullBody = """
                %s

                ---
                Sent from: %s
                User-Agent: %s
                Time: %s
                """.formatted(
                    r.getText(),
                    clientIp,
                    userAgent,
                    new Date()
            );

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(r.getTo());
            message.setSubject(r.getSubject());
            message.setText(fullBody);
            mailSender.send(message);
        } catch (MailSendException e) {
            throw new BusinessException(ErrorCode.SEND_MAIL_ERROR);
        }
    }
}

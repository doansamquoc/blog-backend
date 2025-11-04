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
    public void sendHTMLMail(MailRequest r, HttpServletRequest request) {
        String resetLink = "https://localhost:8080";

        String htmlContent = """
        <html>
           <body style="display: flex; flex-direction: column; align-items: center; background: #b7ccf5;">
               <h2 style="text-align: center;">Your password has been changed!</h2>
               <p>If this wasn't you, please reset your password immediately.</p>
               <a href="http://localhost:8080"
                   style=" display:inline-block;padding:10px 20px;background-color:#007BFF;color:#ffffff;text-decoration:none;border-radius:5px;">
                   Reset password now</a>
               <hr>
               <h4>IP: 127.0.0.1</h4>
               <h4>Device: Iphone 17 Pro Max</h4>
               <h4>Time: 22/12/2024 15:00:32</h4>
           </body>
               </html>
        """.formatted(resetLink);
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

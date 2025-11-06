package com.sam.blog_auth.event.listener;

import com.sam.blog_auth.event.PasswordChangedEvent;
import com.sam.blog_auth.event.PasswordResetEvent;
import com.sam.blog_mailer.dto.request.MailRequest;
import com.sam.blog_mailer.service.MailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetListener {
    MailService mailService;

    @Async
    @EventListener
    public void handlePasswordResetListener(PasswordResetEvent event) {
        MailRequest mailRequest = MailRequest.builder()
                .to(event.getUserEmail())
                .token(event.getToken())
                .build();
        mailService.sendPasswordResetMail(mailRequest, event.getRequest());
    }
}

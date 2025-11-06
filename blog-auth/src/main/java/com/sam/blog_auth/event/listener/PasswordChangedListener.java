package com.sam.blog_auth.event.listener;

import com.sam.blog_mailer.dto.request.MailRequest;
import com.sam.blog_mailer.service.MailService;
import com.sam.blog_auth.event.PasswordChangedEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordChangedListener {
    MailService mailService;

    @Async
    @EventListener
    public void handlePasswordChangedListener(PasswordChangedEvent event) {
        MailRequest mailRequest = MailRequest.builder().to(event.getUserEmail()).token(event.getToken()).build();
        mailService.sendPasswordChangedMail(mailRequest, event.getRequest());
    }
}

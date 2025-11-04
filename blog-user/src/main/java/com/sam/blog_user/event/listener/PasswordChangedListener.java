package com.sam.blog_user.event.listener;

import com.sam.blog_mailer.dto.request.MailRequest;
import com.sam.blog_mailer.service.MailService;
import com.sam.blog_user.event.PasswordChangedEvent;
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
    public void handlePasswordListener(PasswordChangedEvent event) {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setTo(event.getUserEmail());
        mailRequest.setSubject("UPDATE PASSWORD");
        mailRequest.setText("Your password has been changed!");

        mailService.sendTextMail(mailRequest, event.getRequest());
    }
}

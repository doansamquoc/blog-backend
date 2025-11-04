package com.sam.blog_user.event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PasswordChangedEvent extends ApplicationEvent {
    String userEmail;
    HttpServletRequest request;

    public PasswordChangedEvent(Object source, String userEmail, HttpServletRequest request) {
        super(source);
        this.userEmail = userEmail;
        this.request = request;
    }
}

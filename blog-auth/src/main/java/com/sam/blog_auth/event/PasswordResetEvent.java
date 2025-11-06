package com.sam.blog_auth.event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetEvent extends ApplicationEvent {
    String userEmail;
    String token;
    HttpServletRequest request;

    public PasswordResetEvent(Object source, String userEmail, String token, HttpServletRequest request) {
        super(source);
        this.userEmail = userEmail;
        this.token = token;
        this.request = request;
    }
}

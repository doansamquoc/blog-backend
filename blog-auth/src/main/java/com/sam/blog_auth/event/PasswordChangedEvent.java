package com.sam.blog_auth.event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordChangedEvent extends ApplicationEvent {
    final String userEmail;
    final String token;
    final HttpServletRequest request;

    public PasswordChangedEvent(Object source, String userEmail, String token, HttpServletRequest request) {
        super(source);
        this.userEmail = userEmail;
        this.request = request;
        this.token = token;
    }
}

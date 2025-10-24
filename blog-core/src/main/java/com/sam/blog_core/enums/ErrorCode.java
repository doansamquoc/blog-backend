package com.sam.blog_core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Token expired"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access denied"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED, "User not logged in"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email already exists"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid email or password"),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "Username already exists"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    MISSING_FIELD(HttpStatus.BAD_REQUEST, "Missing field");
    HttpStatus status;
    String message;
}

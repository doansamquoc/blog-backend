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
    MISSING_FIELD(HttpStatus.BAD_REQUEST, "Missing field"),
    INVALID_EMAIL_ADDRESS(HttpStatus.BAD_REQUEST, "Invalid email address"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "The password must have at least 8 characters"),
    INVALID_USERNAME(HttpStatus.BAD_REQUEST,"Username must be between 4 and 16 characters"),
    INVALID_FIRSTNAME(HttpStatus.BAD_REQUEST, "First name must be between 4 and 54 characters"),
    INVALID_LASTNAME(HttpStatus.BAD_REQUEST, "Last name must be between 4 and 54 characters"),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "Gender cannot be left blank"),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "Role cannot be left blank");
    HttpStatus status;
    String message;
}

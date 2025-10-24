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
    INVALID_FIRST_NAME(HttpStatus.BAD_REQUEST, "First name must be between 4 and 54 characters"),
    INVALID_LAST_NAME(HttpStatus.BAD_REQUEST, "Last name must be between 4 and 54 characters"),
    INVALID_GENDER(HttpStatus.BAD_REQUEST, "Gender cannot be left blank"),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "Role cannot be left blank"),

    EMAIL_ADDRESS_REQUIRED(HttpStatus.BAD_REQUEST, "Email address required"),
    PASSWORD_REQUIRED(HttpStatus.BAD_REQUEST, "Password is required"),
    FIRST_NAME_REQUIRED(HttpStatus.BAD_REQUEST, "First name is required"),
    DOB_REQUIRED(HttpStatus.BAD_REQUEST, "Date of birth is required"),
    DOB_MUST_BE_IN_THE_PAST(HttpStatus.BAD_REQUEST, "Date of birth must be in the past"),

    SEND_MAIL_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "Send mail error");
    HttpStatus status;
    String message;
}

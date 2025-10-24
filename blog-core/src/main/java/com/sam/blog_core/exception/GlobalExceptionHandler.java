package com.sam.blog_core.exception;

import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import com.sam.blog_core.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

import static com.sam.blog_core.dto.response.ApiResponseFactory.error;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusiness(BusinessException e, HttpServletRequest r) {
        ErrorCode code = e.getErrorCode();
        return ApiResponseFactory.error(code, r.getServletPath());
    }

    /**
     * enumKey contained the error code defined in ErrorCode
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(
            MethodArgumentNotValidException e,
            HttpServletRequest r
    ) {
        String enumKey = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        ErrorCode code = ErrorCode.valueOf(enumKey);
        return ApiResponseFactory.error(code, r.getServletPath());
    }

    @ExceptionHandler({
            BadCredentialsException.class,
            InternalAuthenticationServiceException.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleAuthErrors(Exception exception, HttpServletRequest r) {
        ErrorCode code = ErrorCode.INVALID_CREDENTIALS;
        return ApiResponseFactory.error(code, r.getServletPath());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception e, HttpServletRequest r) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        String message = e.getMessage();
        return ApiResponseFactory.error(errorCode, r.getServletPath(), message);
    }
}

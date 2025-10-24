package com.sam.blog_core.exception;

import com.sam.blog_core.dto.response.ApiResponse;
import com.sam.blog_core.dto.response.ApiResponseFactory;
import com.sam.blog_core.enums.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.sam.blog_core.dto.response.ApiResponseFactory.error;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusiness(BusinessException e, HttpServletRequest r) {
        ErrorCode code = e.getErrorCode();
        return ApiResponseFactory.error(code, r.getServletPath());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException e, HttpServletRequest r) {
        String message = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        ErrorCode code = ErrorCode.MISSING_FIELD;
        return ApiResponseFactory.error(code, r.getServletPath());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception e, HttpServletRequest r) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        String message = e.getMessage();
        return ApiResponseFactory.error(errorCode, r.getServletPath(), message);
    }
}

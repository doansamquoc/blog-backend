package com.sam.blog_core.dto.response;

import com.sam.blog_core.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ApiResponseFactory {
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(ErrorCode errorCode, String path) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .success(false)
                .status(errorCode.getStatus().value())
                .errorCode(errorCode.name())
                .message(errorCode.getMessage())
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(apiResponse);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(ErrorCode errorCode, String path, String customMessage) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .success(false)
                .status(errorCode.getStatus().value())
                .errorCode(errorCode.name())
                .message(customMessage)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(apiResponse);
    }
}

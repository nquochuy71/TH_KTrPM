package com.cosmeticshop.cosmeticshopbe.shared.exception;

import com.cosmeticshop.cosmeticshopbe.shared.response.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Xử lý các exception gom nhóm chung chưa định nghĩa tới (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        log.error("Uncategorized error: ", e);
        ApiErrorResponse response = new ApiErrorResponse(
                ErrorCode.UNCATEGORIZED_EXCEPTION.getCode(),
                ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage()
        );
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode()).body(response);
    }

    // Xử lý custom exception của ứng dụng
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiErrorResponse> handleAppException(AppException e) {
        log.warn("AppException: {}", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();
        ApiErrorResponse response = new ApiErrorResponse(errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
    }

    // Xử lý lỗi validation dữ liệu (VD: @Valid object trong request body)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String validationMessage = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.warn("Validation error: {}", validationMessage);

        ApiErrorResponse response = new ApiErrorResponse(
                ErrorCode.VALIDATION_ERROR.getCode(),
                validationMessage
        );
        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getStatusCode()).body(response);
    }

    // Xử lý lỗi validation param/path variable (VD: @Min, @Max trên controller)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        ApiErrorResponse response = new ApiErrorResponse(
                ErrorCode.VALIDATION_ERROR.getCode(),
                e.getMessage()
        );
        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getStatusCode()).body(response);
    }

    // Lỗi khi user không có quyền truy cập
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        ApiErrorResponse response = new ApiErrorResponse(
                ErrorCode.UNAUTHORIZED.getCode(),
                ErrorCode.UNAUTHORIZED.getMessage()
        );
        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getStatusCode()).body(response);
    }

    // Lỗi khi nhập sai email hoặc password
    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(org.springframework.security.authentication.BadCredentialsException e) {
        ApiErrorResponse response = new ApiErrorResponse(
                ErrorCode.INVALID_CREDENTIALS.getCode(),
                ErrorCode.INVALID_CREDENTIALS.getMessage()
        );
        return ResponseEntity.status(ErrorCode.INVALID_CREDENTIALS.getStatusCode()).body(response);
    }

    // Lỗi liên quan đến xác thực (chưa đăng nhập/token sai)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException e) {
        ApiErrorResponse response = new ApiErrorResponse(
                ErrorCode.UNAUTHENTICATED.getCode(),
                ErrorCode.UNAUTHENTICATED.getMessage()
        );
        return ResponseEntity.status(ErrorCode.UNAUTHENTICATED.getStatusCode()).body(response);
    }

    // Lỗi không tìm thấy endpoint (404) 
    // Yêu cầu cần thêm cấu hình spring.mvc.throw-exception-if-no-handler-found=true trong application.yml
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        ApiErrorResponse response = new ApiErrorResponse(
                ErrorCode.RESOURCE_NOT_FOUND.getCode(),
                "API Endpoint không tồn tại: " + e.getRequestURL()
        );
        return ResponseEntity.status(ErrorCode.RESOURCE_NOT_FOUND.getStatusCode()).body(response);
    }
}

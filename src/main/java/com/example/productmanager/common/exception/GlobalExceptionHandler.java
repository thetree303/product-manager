package com.example.productmanager.common.exception;

import com.example.productmanager.common.exception.dto.ErrorResponse;
import com.example.productmanager.common.exception.dto.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice   // Danh dau xu ly toan bo loi controller
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
        ErrorResponse res = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException e,
            HttpServletRequest request
    ) {
        ErrorResponse res = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    // Xu ly exception khi validate dto
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        Map<String, String> fieldErrors = new HashMap<>();      // Map luu cac loi validation con

        e.getBindingResult().getFieldErrors()       // Truy xuat danh sach loi
                .forEach(err -> fieldErrors.put(
                        err.getField(),
                        err.getDefaultMessage()
                ));     // Day cac loi vao map fieldErrors

        ValidationErrorResponse res = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                fieldErrors,
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }


}

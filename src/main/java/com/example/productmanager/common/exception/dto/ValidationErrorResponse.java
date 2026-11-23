package com.example.productmanager.common.exception.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(
        int status,
        String error,
        Map<String, String> fieldErrors,
        String path,
        LocalDateTime timestamp
) {
}
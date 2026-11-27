package com.example.productmanager.product.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateVariantRequest(
        @Size(max = 50)
        String sku,

        String name,

        @Size(max = 2000)
        String description,

        @PositiveOrZero(message = "Price must not be negative")
        BigDecimal price

        // Khong cho doi product gan voi variant
) {
}

package com.example.productmanager.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateVariantRequest(
        @Size(max = 50)
        String sku,

        String name,

        @Size(max = 2000)
        String description,

        @DecimalMin(value = "0.01", message = "Cost Price must be greater than 0")
        BigDecimal price

        // Khong cho doi product gan voi variant
) {
}

package com.example.productmanager.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateVariantRequest(
        @NotBlank(message = "SKU is not empty")
        @Size(min = 1, max = 50)
        String sku,

        @NotBlank(message = "Name is not empty")
        String name,

        @Size(max = 2000)
        String description,

        @NotNull(message = "Price is not empty")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        BigDecimal price,

        // Cac thuoc tinh cho inventory item
        @NotNull(message = "Cost Price is not empty")
        @DecimalMin(value = "0.01", message = "Cost Price must be greater than 0")
        BigDecimal costPrice,

        Boolean tracked
) {

}

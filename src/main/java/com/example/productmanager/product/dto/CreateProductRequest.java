package com.example.productmanager.product.dto;

import com.example.productmanager.product.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
        @NotBlank(message = "Product name is required")
        String name,

        @Size(max = 2000)
        String description,

        ProductStatus status
) {
}

package com.example.productmanager.product.dto;

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
        BigDecimal price
) {

}

package com.example.productmanager.inventory.dto;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record UpdateInventoryItemRequest(
        Boolean tracked,

        @DecimalMin(value = "0.01", message = "Cost Price must be greater than 0")
        BigDecimal costPrice
) {
}

package com.example.productmanager.inventory.dto;

import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateInventoryItemRequest(
        Boolean tracked,

        @PositiveOrZero(message = "Cost Price must not be negative")
        BigDecimal costPrice
) {
}

package com.example.productmanager.inventory.dto;

import jakarta.validation.constraints.NotNull;

public record InventoryConnectRequest(
        @NotNull(message = "Location ID cannot be empty")
        Long locationId,

        @NotNull(message = "Inventory Item ID cannot be empty")
        Long inventoryItemId
) {
}

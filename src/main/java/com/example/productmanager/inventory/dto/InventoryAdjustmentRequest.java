package com.example.productmanager.inventory.dto;

import com.example.productmanager.inventory.InventoryAdjustmentReason;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InventoryAdjustmentRequest(
        @NotNull(message = "Inventory Item Id cannot be empty")
        Long inventoryItemId,

        @NotNull(message = "Location Id cannot be empty")
        Long locationId,

        @NotBlank(message = "Request Id cannot be empty")
        @Size(min = 1, max = 50)
        String requestId,

        @NotNull(message = "Delta cannot be empty")
        Long delta,

        @NotNull(message = "Reason cannot be empty")
        InventoryAdjustmentReason reason,

        @Size(max = 2000)
        String note
) {
}

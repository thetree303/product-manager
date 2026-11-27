package com.example.productmanager.inventory.dto;

import com.example.productmanager.inventory.InventoryLevel;

import java.time.Instant;

public record InventoryLevelResponse(
        Long id,
        Long inventoryItemId,
        String variantSku,
        String variantName,
        Long locationId,
        String locationCode,
        String locationName,
        Long onHand,
        Long available,
        Instant createdAt,
        Instant updatedAt,
        Long version
) {
    public InventoryLevelResponse(InventoryLevel inventoryLevel) {
        this(
                inventoryLevel.getId(),
                inventoryLevel.getInventoryItem().getId(),
                inventoryLevel.getInventoryItem().getVariant().getSku(),
                inventoryLevel.getInventoryItem().getVariant().getName(),
                inventoryLevel.getLocation().getId(),
                inventoryLevel.getLocation().getCode(),
                inventoryLevel.getLocation().getName(),
                inventoryLevel.getOnHand(),
                inventoryLevel.getAvailable(),
                inventoryLevel.getCreatedAt(),
                inventoryLevel.getUpdatedAt(),
                inventoryLevel.getVersion()
        );
    }
}

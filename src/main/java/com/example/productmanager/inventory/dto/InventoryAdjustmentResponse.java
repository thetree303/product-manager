package com.example.productmanager.inventory.dto;

import com.example.productmanager.inventory.InventoryAdjustment;
import com.example.productmanager.inventory.InventoryAdjustmentReason;

public record InventoryAdjustmentResponse(
        Long id,
        Long inventoryItemId,
        Long inventoryItemVariantId,
        String inventoryItemVariantSku,
        String inventoryItemVariantName,
        Long locationId,
        String locationCode,
        String locationName,
        String requestId,
        Integer delta,
        Integer onHandAfter,
        InventoryAdjustmentReason reason,
        String note
) {
    public InventoryAdjustmentResponse(InventoryAdjustment adjustment) {
        this(
                adjustment.getId(),
                adjustment.getInventoryItem().getId(),
                adjustment.getInventoryItem().getVariant().getId(),
                adjustment.getInventoryItem().getVariant().getSku(),
                adjustment.getInventoryItem().getVariant().getName(),
                adjustment.getLocation().getId(),
                adjustment.getLocation().getCode(),
                adjustment.getLocation().getName(),
                adjustment.getRequestId(),
                adjustment.getDelta(),
                adjustment.getOnHandAfter(),
                adjustment.getReason(),
                adjustment.getNote()
        );
    }
}

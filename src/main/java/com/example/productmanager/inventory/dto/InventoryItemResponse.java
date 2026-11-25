package com.example.productmanager.inventory.dto;

import com.example.productmanager.inventory.InventoryItem;

import java.math.BigDecimal;
import java.time.Instant;

public record InventoryItemResponse(
        Long id,
        Long variantId,
        String variantSku,
        String variantName,
        BigDecimal variantPrice,
        Boolean tracked,
        BigDecimal costPrice,
        Instant createdAt,
        Instant updatedAt
) {
    public InventoryItemResponse(InventoryItem item) {
        this(
                item.getId(),
                item.getVariant().getId(),
                item.getVariant().getSku(),
                item.getVariant().getName(),
                item.getVariant().getPrice(),
                item.isTracked(),
                item.getCostPrice(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}

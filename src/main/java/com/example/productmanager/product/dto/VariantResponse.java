package com.example.productmanager.product.dto;

import com.example.productmanager.product.Variant;

import java.math.BigDecimal;
import java.time.Instant;

public record VariantResponse(
        Long id,
        Long inventoryItemId,
        String sku,
        String name,
        String description,
        BigDecimal price,
        Instant createdAt,
        Instant updatedAt
) {
    public VariantResponse(Variant variant) {
        this(
                variant.getId(),
                variant.getInventoryItem().getId(),
                variant.getSku(),
                variant.getName(),
                variant.getDescription(),
                variant.getPrice(),
                variant.getCreatedAt(),
                variant.getUpdatedAt()
        );
    }
}

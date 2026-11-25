package com.example.productmanager.product.dto;

import com.example.productmanager.product.Product;
import com.example.productmanager.product.ProductStatus;

import java.time.Instant;

public record ProductResponse(
        Long id,
        String name,
        String description,
        ProductStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    // Constructor mapping truc tiep entity sang dto
    public ProductResponse(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}

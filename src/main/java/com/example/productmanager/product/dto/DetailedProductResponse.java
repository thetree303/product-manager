package com.example.productmanager.product.dto;

import com.example.productmanager.product.Product;
import com.example.productmanager.product.ProductStatus;
import com.example.productmanager.product.Variant;

import java.time.Instant;
import java.util.List;

public record DetailedProductResponse(
        Long id,
        String name,
        String description,
        ProductStatus status,
        List<Variant> variants,
        Instant createdAt,
        Instant updatedAt,
        Long version
) {
    // Constructor mapping truc tiep entity sang dto
    public DetailedProductResponse(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getStatus(),
                product.getVariants(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getVersion()
        );
    }
}

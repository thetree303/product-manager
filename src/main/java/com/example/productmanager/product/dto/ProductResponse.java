package com.example.productmanager.product.dto;

import com.example.productmanager.product.Product;
import com.example.productmanager.product.ProductStatus;

import java.time.Instant;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        ProductStatus status,
        List<VariantResponse> variants,
        Instant createdAt,
        Instant updatedAt,
        Long version
) {
    // Constructor mapping truc tiep entity sang dto
    public ProductResponse(Product product, List<VariantResponse> variants) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getStatus(),
                variants,
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getVersion()
        );
    }
}

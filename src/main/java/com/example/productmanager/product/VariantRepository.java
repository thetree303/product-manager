package com.example.productmanager.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VariantRepository extends JpaRepository<Variant, Long> {
    Optional<Variant> findBySku(String sku);

    // Ho tro kiem tra trung sku
    public Boolean existsBySku(String sku);
}

package com.example.productmanager.product;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface VariantRepository extends JpaRepository<Variant, Long> {
    Optional<Variant> findBySku(String sku);

    // Ho tro kiem tra trung sku
    Boolean existsBySku(String sku);

    @EntityGraph(attributePaths = "inventoryItem")
    List<Variant> findBySkuIn(List<String> skus);

    // Truy van buoc 2: Tim cac Variant voi product id nam trong danh sach
    @EntityGraph(attributePaths = "inventoryItem")
    List<Variant> findByProductIdIn(Collection<Long> productIds);

    @EntityGraph(attributePaths = "inventoryItem")
    List<Variant> findByProductId(Long productId);
}

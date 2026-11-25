package com.example.productmanager.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByVariantId(Long variantId);

    @EntityGraph(attributePaths = "variant")
    Page<InventoryItem> findAll(Pageable pageable);
}

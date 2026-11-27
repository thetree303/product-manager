package com.example.productmanager.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryAdjustmentRepository extends JpaRepository<InventoryAdjustment, Long> {
    @EntityGraph(attributePaths = {"inventoryItem", "location", "inventoryItem.variant"})
    Page<InventoryAdjustment> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"inventoryItem", "location", "inventoryItem.variant"})
    Page<InventoryAdjustment> findByInventoryItemIdAndLocationId(
            Long inventoryId,
            Long locationId,
            Pageable pageable
    );

    Boolean existsByRequestId(String requestId);
}

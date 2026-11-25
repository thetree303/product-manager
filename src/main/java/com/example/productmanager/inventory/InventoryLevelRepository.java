package com.example.productmanager.inventory;

import com.example.productmanager.location.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryLevelRepository extends JpaRepository<InventoryLevel, Long> {
    boolean existsByInventoryItemAndLocation(
            InventoryItem inventoryItem,
            Location location
    );

    @EntityGraph(attributePaths = {"location", "inventoryItem", "inventoryItem.variant"})
    Optional<InventoryLevel> findByInventoryItemAndLocation(
            InventoryItem inventoryItem,
            Location location
    );

    @EntityGraph(attributePaths = {"location", "inventoryItem", "inventoryItem.variant"})
    Page<InventoryLevel> findByInventoryItemIdAndLocationId(
            Long inventoryItemId,
            Long locationId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"location", "inventoryItem", "inventoryItem.variant"})
    Page<InventoryLevel> findByInventoryItemId(
            Long inventoryItemId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"location", "inventoryItem", "inventoryItem.variant"})
    Page<InventoryLevel> findByLocationId(
            Long locationId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"location", "inventoryItem", "inventoryItem.variant"})
    Page<InventoryLevel> findAll(
            Pageable pageable
    );
}

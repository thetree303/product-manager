package com.example.productmanager.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryLevelRepository extends JpaRepository<InventoryLevel, Long> {
}

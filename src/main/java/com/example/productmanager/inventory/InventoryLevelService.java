package com.example.productmanager.inventory;

import com.example.productmanager.common.exception.BusinessException;
import com.example.productmanager.common.exception.ResourceNotFoundException;
import com.example.productmanager.inventory.dto.InventoryLevelResponse;
import com.example.productmanager.location.Location;
import com.example.productmanager.location.LocationRepository;
import com.example.productmanager.location.LocationStatus;
import com.example.productmanager.product.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryLevelService {
    private final InventoryLevelRepository inventoryLevelRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public InventoryLevel create(
            Location location,
            InventoryItem inventoryItem
    ) {
        // Kiem tra trung: chi co 1 inventory level cho 1 cap inventory item va location
        if (inventoryLevelRepository.existsByInventoryItemAndLocation(inventoryItem, location)) {
            throw new BusinessException("Inventory already exists");
        }

        InventoryLevel inventoryLevel = new InventoryLevel();
        inventoryLevel.setLocation(location);
        inventoryLevel.setInventoryItem(inventoryItem);
        // Khoi tao mac dinh onHand va available la 0.
        // Nguoi dung se thay doi qua thao tac xuat/nhap kho sau
        inventoryLevel.setOnHand(0L);
        inventoryLevel.setAvailable(0L);

        return inventoryLevelRepository.save(inventoryLevel);
    }

    // Ket noi inventory item toi location
    @Transactional
    public InventoryLevel connect(
            Long inventoryItemId,
            Long locationId
    ) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(inventoryItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory Item not found"));
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location Not Found"));

        // Khong cho ket noi neu dang archived
        if (ProductStatus.ARCHIVED.equals(inventoryItem.getVariant().getProduct().getStatus())) {
            throw new BusinessException("Cannot connect archived product");
        }

        // Khong cho ket noi neu kho khong hoat dong
        if (LocationStatus.INACTIVE.equals(location.getStatus())) {
            throw new BusinessException("Cannot connect product to inactive location");
        }

        return create(location, inventoryItem);
    }

    // Tra cuu danh sach ton kho
    @Transactional(readOnly = true)
    public Page<InventoryLevelResponse> getInventoryLevel(
            Pageable pageable,
            Long inventoryItemId,
            Long locationId
    ) {
        Page<InventoryLevel> page;

        if (locationId != null && inventoryItemId != null) {
            page = inventoryLevelRepository
                    .findByInventoryItemIdAndLocationId(inventoryItemId, locationId, pageable);
        } else if (locationId != null) {
            page = inventoryLevelRepository
                    .findByLocationId(locationId, pageable);
        } else if (inventoryItemId != null) {
            page = inventoryLevelRepository
                    .findByInventoryItemId(inventoryItemId, pageable);
        } else {
            page = inventoryLevelRepository
                    .findAll(pageable);
        }

        return page.map(InventoryLevelResponse::new);
    }

    // Cap nhat xuat/nhap kho
    // Hien tai tam thoi dieu chinh ca available va onHand
    // Se dieu chinh sau de phu hop voi nghiep vu thuc te
    @Transactional
    public InventoryLevel adjust(InventoryLevel inventoryLevel, Long delta) {
        if (Math.addExact(inventoryLevel.getAvailable(), delta) < 0) {
            throw new BusinessException("Insufficient items. Cannot export");
        }
        inventoryLevel.setOnHand(Math.addExact(inventoryLevel.getOnHand(), delta));
        inventoryLevel.setAvailable(Math.addExact(inventoryLevel.getAvailable(), delta));
        return inventoryLevelRepository.save(inventoryLevel);
    }
}

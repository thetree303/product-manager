package com.example.productmanager.inventory;

import com.example.productmanager.common.exception.BusinessException;
import com.example.productmanager.common.exception.ResourceNotFoundException;
import com.example.productmanager.inventory.dto.InventoryAdjustmentRequest;
import com.example.productmanager.inventory.dto.InventoryAdjustmentResponse;
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
public class InventoryAdjustmentService {
    private final InventoryItemRepository inventoryItemRepository;
    private final LocationRepository locationRepository;
    private final InventoryLevelRepository inventoryLevelRepository;
    private final InventoryLevelService inventoryLevelService;
    private final InventoryAdjustmentRepository inventoryAdjustmentRepository;

    // Xuat/nhap kho
    @Transactional
    public InventoryAdjustment create(InventoryAdjustmentRequest req) {
        // Kiem tra trung request ID
        if (inventoryAdjustmentRepository.existsByRequestId(req.requestId())) {
            throw new BusinessException("Request ID already exists");
        }

        InventoryItem inventoryItem = inventoryItemRepository.findById(req.inventoryItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventory Item not found"));
        Location location = locationRepository.findById(req.locationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        // Khong cho xuat/nhap neu dang archived
        if (ProductStatus.ARCHIVED.equals(inventoryItem.getVariant().getProduct().getStatus())) {
            throw new BusinessException("Cannot export/import archived product");
        }

        // Khong cho xuat/nhap neu kho khong hoat dong
        if (LocationStatus.INACTIVE.equals(location.getStatus())) {
            throw new BusinessException("Cannot export/import product at inactive location");
        }

        // Tim inventory level tuong ung va thay doi
        InventoryLevel inventoryLevel = inventoryLevelRepository
                .findByInventoryItemAndLocation(inventoryItem, location)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory Level not found"));
        Long onHandAfter = inventoryLevelService.adjust(inventoryLevel, req.delta()).getOnHand();

        // Luu ban ghi adjustment
        InventoryAdjustment inventoryAdjustment = new InventoryAdjustment(
                inventoryItem,
                location,
                req.requestId(),
                req.delta(),
                onHandAfter,
                req.reason(),
                req.note()
        );
        return inventoryAdjustmentRepository.save(inventoryAdjustment);
    }


    // Lay toan bo ban ghi adjustment
    @Transactional(readOnly = true)
    public Page<InventoryAdjustmentResponse> findAll(Pageable pageable) {
        return inventoryAdjustmentRepository.findAll(pageable).map(InventoryAdjustmentResponse::new);
    }
}

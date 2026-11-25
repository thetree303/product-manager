package com.example.productmanager.inventory;

import com.example.productmanager.common.exception.ResourceNotFoundException;
import com.example.productmanager.inventory.dto.InventoryItemResponse;
import com.example.productmanager.location.Location;
import com.example.productmanager.location.LocationRepository;
import com.example.productmanager.product.Variant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryLevelService inventoryLevelService;
    private final LocationRepository locationRepository;

    @Transactional
    // Chi duoc goi boi variant service, khong tao api
    public InventoryItemResponse create(
            Variant variant,
            boolean tracked,
            BigDecimal costPrice
    ) {
        // Tao va luu inventory item moi
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setTracked(tracked);
        inventoryItem.setCostPrice(costPrice);
        inventoryItem.setVariant(variant);
        InventoryItem savedInventoryItem = inventoryItemRepository.save(inventoryItem);

        // Tao moi inventory level tai kho hang mac dinh neu duoc tracked
        Optional<Location> defaultLocation = locationRepository.findByDefaultLocationTrue();
        if (tracked && defaultLocation.isPresent()) {
            inventoryLevelService.create(defaultLocation.get(), savedInventoryItem);
        }

        return new InventoryItemResponse(inventoryItem);
    }

    @Transactional(readOnly = true)
    public Page<InventoryItemResponse> findAll(Pageable pageable) {
        return inventoryItemRepository
                .findAll(pageable)
                .map(InventoryItemResponse::new);
    }

    @Transactional(readOnly = true)
    public InventoryItemResponse findById(Long id) {
        InventoryItem res = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory Item not found"));
        return new InventoryItemResponse(res);
    }

    @Transactional(readOnly = true)
    public InventoryItemResponse findByVariantId(Long variantId) {
        InventoryItem res = inventoryItemRepository.findByVariantId(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("No Inventory Item found"));
        return new InventoryItemResponse(res);
    }
}

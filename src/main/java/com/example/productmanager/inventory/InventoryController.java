package com.example.productmanager.inventory;

import com.example.productmanager.inventory.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryItemService inventoryItemService;
    private final InventoryLevelService inventoryLevelService;
    private final InventoryAdjustmentService inventoryAdjustmentService;

    // Ket noi inventory item toi kho khac
    @PostMapping("/connect")
    public ResponseEntity<InventoryLevelResponse> connectInventoryItem(
            @RequestBody @Valid InventoryConnectRequest req
    ) {
        InventoryLevel res = inventoryLevelService.connect(req.inventoryItemId(), req.locationId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new InventoryLevelResponse(res));
    }

    // Tra cuu danh sach ton kho
    @GetMapping()
    public ResponseEntity<Page<InventoryLevelResponse>> getInventoryLevel(
            @RequestParam(required = false) Long inventoryItemId,
            @RequestParam(required = false) Long locationId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(inventoryLevelService.getInventoryLevel(
                pageable,
                inventoryItemId,
                locationId
        ));
    }

    // Xem lich su bien dong kho
    @GetMapping("/adjustments")
    public ResponseEntity<Page<InventoryAdjustmentResponse>> getAdjustments(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(inventoryAdjustmentService.findAll(pageable));
    }

    // Xuat/nhap kho
    @PostMapping("/adjust")
    public ResponseEntity<InventoryAdjustmentResponse> adjustInventoryItem(
            @Valid @RequestBody InventoryAdjustmentRequest req
    ) {
        InventoryAdjustment res = inventoryAdjustmentService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(new InventoryAdjustmentResponse(res));
    }

    // Cap nhat item
    @PatchMapping("/items/{id}")
    public ResponseEntity<InventoryItemResponse> updateInventoryItem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateInventoryItemRequest req
    ) {
        InventoryItemResponse res = inventoryItemService.update(id, req);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}

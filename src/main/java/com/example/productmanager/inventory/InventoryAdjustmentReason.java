package com.example.productmanager.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter                         // Tự động sinh hàm getValue, không cần viết thêm hàm
@RequiredArgsConstructor        // Tự động sinh constructor với tham số đầu vào
public enum InventoryAdjustmentReason {
    INVENTORY_INITIALIZE("inventory_initialize"),
    INVENTORY_ADJUST("inventory_adjust"),
    INVENTORY_CHANGE_BY_DAMAGED("inventory_change_by_damaged"),
    INVENTORY_CHANGE_BY_CORRECTION("inventory_change_by_correction");

    @JsonValue                  // Tự serialize chữ hoa ra JSON thành chữ thường
    private final String value;

    // Method map enum snake_case sang thành enum trong java tương ứng
    @JsonCreator
    public static InventoryAdjustmentReason fromValue(String value) {
        for (InventoryAdjustmentReason reason : InventoryAdjustmentReason.values()) {
            if (reason.value.equals(value)) {
                return reason;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}

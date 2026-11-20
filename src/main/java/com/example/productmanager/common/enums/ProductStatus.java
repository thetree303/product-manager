package com.example.productmanager.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter                         // Tự động sinh hàm getValue, không cần viết thêm hàm
@RequiredArgsConstructor        // Tự động sinh constructor với tham số đầu vào
public enum ProductStatus {
    DRAFT("draft"),
    ACTIVE("active"),
    ARCHIVED("archived");

    @JsonValue                  // Tự serialize chữ hoa ra JSON thành chữ thường
    private final String value;

    // Method map enum snake_case sang thành enum trong java tương ứng
    @JsonCreator
    public static ProductStatus fromValue(String value) {
        for (ProductStatus status : ProductStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}

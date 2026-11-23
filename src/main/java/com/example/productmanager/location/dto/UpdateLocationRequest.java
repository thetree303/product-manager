package com.example.productmanager.location.dto;

import com.example.productmanager.location.LocationStatus;
import jakarta.validation.constraints.Size;

public record UpdateLocationRequest(
        @Size(max = 50)
        String code,

        String name,

        @Size(max = 2000)
        String description,

        LocationStatus status

        // Khong update trang thai mac dinh, ta tach thanh API rieng
) {
}

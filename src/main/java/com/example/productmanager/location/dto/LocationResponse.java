package com.example.productmanager.location.dto;

import com.example.productmanager.location.Location;
import com.example.productmanager.location.LocationStatus;

import java.time.Instant;

public record LocationResponse(
        Long id,
        String code,
        String name,
        String description,
        LocationStatus status,
        Boolean defaultLocation,
        Instant createdAt,
        Instant updatedAt
) {
    // Constructor mapping truc tiep entity sang dto
    public LocationResponse(Location location) {
        this(
                location.getId(),
                location.getCode(),
                location.getName(),
                location.getDescription(),
                location.getStatus(),
                location.getDefaultLocation(),
                location.getCreatedAt(),
                location.getUpdatedAt()
        );
    }
}

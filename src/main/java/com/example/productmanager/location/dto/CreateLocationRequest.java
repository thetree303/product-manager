package com.example.productmanager.location.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateLocationRequest(
        @NotBlank(message = "Location code is required")
        @Size(max = 50)
        String code,

        @NotBlank(message = "Location name is required")
        String name,

        @Size(max = 2000)
        String description,
        
        Boolean defaultLocation
) {

}

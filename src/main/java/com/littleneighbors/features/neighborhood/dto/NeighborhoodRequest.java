package com.littleneighbors.features.neighborhood.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NeighborhoodRequest {

    @NotBlank(message =  "City name is required")
    private String cityName;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Street name is required")
    private String streetName;
}

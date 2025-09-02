package com.littleneighbors.features.family.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FamilyLocationRequest {
    @NotBlank(message = "Neighborhood cannot be blank")
    private Long neighborhoodId;

    @NotBlank(message = "Postal code cannot be blank")
    private String postalCode;
}

package com.littleneighbors.features.family.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyRequest {
    @NotBlank(message = "Representative name is require")
    private String representativeName;

    @NotBlank(message = "Neighborhood is required")
    private String neighborhood;

    private String area;
    private Long userId;
}

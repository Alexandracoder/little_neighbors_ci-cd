package com.littleneighbors.features.family.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyRequest {
    @NotBlank(message = "Representative name is required")
    private String representativeName;

    @NotNull(message = "Neighborhood is required")
    private Long neighborhoodId;

    private String area;

    private List<Long> ChildrenIds;
}

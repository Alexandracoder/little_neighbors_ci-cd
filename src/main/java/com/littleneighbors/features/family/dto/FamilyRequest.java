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
    @NotBlank(message = "Family name is required")
    private String familyName;
    @NotBlank(message = "Description is required")
    private String description;
    private String profilePictureUrl;
    private List<Long> childrenIds;
    private Long userId;
}

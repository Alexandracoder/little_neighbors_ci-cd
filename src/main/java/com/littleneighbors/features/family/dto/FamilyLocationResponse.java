package com.littleneighbors.features.family.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FamilyLocationResponse {
    private String neighborhoodName;
    private String postalCode;
}

package com.littleneighbors.features.neighborhood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NeighborhoodResponse {
    private Long id;
    private String cityName;
    private String name;
    private String streetName;
}

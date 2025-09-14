package com.littleneighbors.features.neighborhood.mapper;

import com.littleneighbors.features.family.mapper.FamilyMapper;
import com.littleneighbors.features.neighborhood.dto.NeighborhoodRequest;
import com.littleneighbors.features.neighborhood.dto.NeighborhoodResponse;
import com.littleneighbors.features.neighborhood.entity.Neighborhood;
import com.littleneighbors.shared.mapper.GenericMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class NeighborhoodMapper extends GenericMapper<NeighborhoodRequest, NeighborhoodResponse, Neighborhood> {

    private final FamilyMapper familyMapper;

    public NeighborhoodMapper(FamilyMapper familyMapper) {
        this.familyMapper = familyMapper;
    }

    @Override
    public Neighborhood fromRequest(@NotNull NeighborhoodRequest request) {
      return Neighborhood.builder()
              .cityName(request.getCityName())
              .name(request.getName())
              .streetName(request.getStreetName())
              .build();
    }

    @Override
    public NeighborhoodResponse toResponse(Neighborhood entity) {
        NeighborhoodResponse neighborhoodResponse = NeighborhoodResponse.builder()
                .id(entity.getId())
                .cityName(entity.getCityName())
                .name(entity.getName())
                .streetName(entity.getStreetName())
                .build();

        return neighborhoodResponse;
    }
}



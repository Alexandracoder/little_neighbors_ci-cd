package com.littleneighbors.features.family.mapper;
import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.neighborhood.model.Neighborhood;
import com.littleneighbors.features.neighborhood.repository.NeighborhoodRepository;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import com.littleneighbors.shared.mapper.GenericMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FamilyMapper implements GenericMapper<Family,FamilyRequest, FamilyResponse> {

    private final NeighborhoodRepository neighborhoodRepository;

    public FamilyMapper(NeighborhoodRepository neighborhoodRepository) {
        this.neighborhoodRepository = neighborhoodRepository;
    }
    @Override
    public  Family fromRequest(FamilyRequest request) {
        if (request == null) return null;

        Family family = new Family();
        family.setRepresentativeName(request.getRepresentativeName());
        family.setArea(request.getArea());

        Neighborhood neighborhood = neighborhoodRepository.findByName(request.getNeighborhood())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Neighborhood not found with name: " + request.getNeighborhood()));
        family.setNeighborhood(neighborhood);

        return family;
    }
    @Override
    public FamilyResponse toResponse(Family family) {
        if (family == null) return null;

        FamilyResponse response = new FamilyResponse();
        response.setId(family.getId());
        response.setRepresentativeName(family.getRepresentativeName());
        response.setNeighborhood(family.getNeighborhood().getName());
        response.setArea(family.getArea());

        if (family.getChildren() != null) {
            response.setChildrenIds(
                    family.getChildren().stream()
                            .map(child -> child.getId())
                            .collect(Collectors.toList())
            );
        }

        response.setCreatedAt(family.getCreatedAt());
        response.setUpdatedAt(family.getUpdatedAt());

        return response;
    }

    @Override
    public List<FamilyResponse> toResponseList(List<Family> entities) {
        return List.of();
    }
}
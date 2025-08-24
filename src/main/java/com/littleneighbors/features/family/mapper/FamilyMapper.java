package com.littleneighbors.features.family.mapper;

import com.littleneighbors.features.child.mapper.ChildMapper;
import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.neighborhood.model.Neighborhood;
import com.littleneighbors.shared.mapper.GenericMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class FamilyMapper extends GenericMapper<FamilyRequest, FamilyResponse, Family> {

    private final ChildMapper childMapper;

    public FamilyMapper(ChildMapper childMapper) {
        this.childMapper = childMapper;
    }

    @Override
    public Family fromRequest(@NotNull FamilyRequest request) {
        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setId(request.getNeighborhoodId());

        Family family = Family.builder()
                .representativeName(request.getRepresentativeName())
                .neighborhood(neighborhood)
                .build();
        return family;
    }

    @Override
    public FamilyResponse toResponse(Family entity) {
        FamilyResponse response = FamilyResponse.builder()
                .id(entity.getId())
                .representativeName(entity.getRepresentativeName())
                .neighborhoodId(entity.getNeighborhood() != null ? entity.getNeighborhood().getId() : null)
                .build();

        if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
            response.setChildren(childMapper.toResponseList(entity.getChildren()));
        }

        return response;
    }
}



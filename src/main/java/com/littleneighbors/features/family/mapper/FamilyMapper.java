package com.littleneighbors.features.family.mapper;

import com.littleneighbors.features.child.mapper.ChildMapper;
import com.littleneighbors.features.family.dto.ChildInfo;
import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.entity.Family;
import com.littleneighbors.features.neighborhood.entity.Neighborhood;
import com.littleneighbors.shared.mapper.GenericMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

        return Family.builder()
                .representativeName(request.getRepresentativeName())
                .neighborhood(neighborhood)
                .description(request.getFamilyName())
                .familyName(request.getFamilyName())
                .profilePictureUrl(request.getProfilePictureUrl())
                .build();
    }

    @Override
    public FamilyResponse toResponse(Family entity) {
        List<ChildInfo> childrenInfo = entity.getChildren() == null ? List.of() :
        entity.getChildren().stream()
                .map(ChildInfo::fromEntity)
                .collect(Collectors.toList());

        return FamilyResponse.builder()
                .id(entity.getId())
                .representativeName(entity.getRepresentativeName())
                .neighborhoodId(entity.getNeighborhood() != null ? entity.getNeighborhood().getId() : null)
                .build();
    }
}



package com.littleneighbors.features.family.mapper;

import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.model.Family;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FamilyMapper {

    public static Family toEntity(FamilyRequest request) {
        if (request == null) {
            return null;
        }
        Family family = new Family();
        family.setRepresentativeName(request.getRepresentativeName());
        family.setNeighborhood(request.getNeighborhood());
        family.setArea(request.getArea());
        return family;
        }
        public static FamilyResponse toResponse(Family family) {
        if (family == null) {
            return null;

        }
        FamilyResponse response = new FamilyResponse();
            response.setId(family.getId());
            response.setRepresentativeName(family.getRepresentativeName());
            response.setNeighborhood(family.getNeighborhood());
            response.setArea(family.getArea());

            if(family.getChildren() != null) {
                List<Long> childrenIds = family.getChildren().stream()
                        .map(child -> child.getId())
                        .collect(Collectors.toList());
                response.setChildrenIds(childrenIds);

            }
            response.setCreatedAt(family.getCreatedAt());
            response.setUpdatedAt(family.getUpdatedAt());

            return response;
    }
}
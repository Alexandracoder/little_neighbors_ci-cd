package com.littleneighbors.features.family.service;

import com.littleneighbors.features.family.dto.*;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.neighborhood.model.Neighborhood;
import com.littleneighbors.shared.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FamilyService extends GenericService<FamilyRequest, FamilyResponse, Long> {
    FamilyResponse create(FamilyRequest request);
    FamilyResponse update(Long id, FamilyRequest request);
    FamilyResponse getFamilyByUserId(Long userId);
    List<Family> findByNeighborhoodOrPostalCode(Neighborhood neighborhood, String postalCode);
    Page<FamilyResponse> getFamilies(Pageable pageable);
    FamilyLocationResponse getLocation(Long familyId);
    FamilyLocationResponse updateLocation(Long familyId, FamilyLocationRequest request);

    FamilyResponse updateFamilyLocation(Long userId, FamilyLocationRequest request);
}

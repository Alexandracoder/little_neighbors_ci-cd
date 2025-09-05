package com.littleneighbors.features.family.service;

import com.littleneighbors.features.family.dto.FamilyLocationRequest;
import com.littleneighbors.features.family.dto.FamilyLocationResponse;
import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.neighborhood.model.Neighborhood;
import com.littleneighbors.shared.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FamilyService extends GenericService<FamilyRequest, FamilyResponse, Long> {
    FamilyResponse create(FamilyRequest request);
    FamilyResponse getFamilyByUserId(Long userId);
    Optional<Family> findByUserUsername(String username);
    List<Family> findByNeighborhoodOrPostalCode(Neighborhood neighborhood, String postalCode);
    Page<FamilyResponse> getFamilies(Pageable pageable);
    FamilyResponse updateFamily(Long id, FamilyRequest request);
    FamilyResponse getFamilyByUsername(String username);
    FamilyResponse updateFamilyLocation(Long userId, FamilyLocationRequest request);
    FamilyLocationResponse getLocation(Long familyId);
    FamilyLocationResponse updateLocation(Long familyId, FamilyLocationRequest request);
}




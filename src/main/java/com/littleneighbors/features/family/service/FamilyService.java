package com.littleneighbors.features.family.service;

import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;

import java.util.List;

public interface FamilyService {

    FamilyResponse createFamily(FamilyRequest request);

    FamilyResponse getFamilyById(Long id);

    List<FamilyResponse> getAllFamilies();

    FamilyResponse updateFamily(Long id, FamilyRequest request);

    void deleteFamily(Long id);
}

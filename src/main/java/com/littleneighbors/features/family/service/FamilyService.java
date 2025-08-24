package com.littleneighbors.features.family.service;

import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.shared.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FamilyService extends GenericService<FamilyRequest, FamilyResponse, Long> {
    FamilyResponse create(FamilyRequest request);
    FamilyResponse update(Long id, FamilyRequest request);
    FamilyResponse getFamilyByUserId(Long userId);
    Page<FamilyResponse> getFamilies(Pageable pageable);

}

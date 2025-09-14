package com.littleneighbors.features.neighborhood.service;

import com.littleneighbors.features.neighborhood.dto.NeighborhoodRequest;
import com.littleneighbors.features.neighborhood.dto.NeighborhoodResponse;
import com.littleneighbors.shared.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NeighborhoodService extends GenericService<NeighborhoodRequest, NeighborhoodResponse, Long> {
    NeighborhoodResponse create(NeighborhoodRequest request);
    NeighborhoodResponse update(Long id, NeighborhoodRequest request);
    NeighborhoodResponse getNeighborhoodByUserId(Long userId);
    Page<NeighborhoodResponse> getNeighborhoods(Pageable pageable);
}


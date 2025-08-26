package com.littleneighbors.features.child.service;

import com.littleneighbors.features.child.dto.ChildRequest;
import com.littleneighbors.features.child.dto.ChildResponse;
import com.littleneighbors.shared.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChildService extends GenericService<ChildRequest, ChildResponse,Long> {
    ChildResponse addChildToFamily(Long familyId, ChildRequest request);
    Page<ChildResponse> getChildrenByFamily(Long familyId, Pageable pageable);
}

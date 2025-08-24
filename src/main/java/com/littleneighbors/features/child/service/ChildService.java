package com.littleneighbors.features.child.service;

import com.littleneighbors.features.child.dto.ChildRequest;
import com.littleneighbors.features.child.dto.ChildResponse;
import com.littleneighbors.shared.service.GenericService;

public interface ChildService extends GenericService<ChildRequest, ChildResponse,Long> {
    ChildResponse addChildToFamily(Long familyId, ChildRequest request);
}

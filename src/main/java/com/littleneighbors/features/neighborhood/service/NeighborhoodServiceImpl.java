package com.littleneighbors.features.neighborhood.service;

import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.family.repository.FamilyRepository;
import com.littleneighbors.features.neighborhood.dto.NeighborhoodRequest;
import com.littleneighbors.features.neighborhood.dto.NeighborhoodResponse;
import com.littleneighbors.features.neighborhood.mapper.NeighborhoodMapper;
import com.littleneighbors.features.neighborhood.model.Neighborhood;
import com.littleneighbors.features.neighborhood.repository.NeighborhoodRepository;
import com.littleneighbors.shared.AbstractGenericService;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NeighborhoodServiceImpl extends AbstractGenericService<Neighborhood,
        NeighborhoodRequest, NeighborhoodResponse, Long> implements NeighborhoodService {

    private final NeighborhoodRepository neighborhoodRepository;
    private final FamilyRepository familyRepository;

    public NeighborhoodServiceImpl(NeighborhoodRepository neighborhoodRepository, FamilyRepository familyRepository, NeighborhoodMapper neighborhoodMapper) {
        super(neighborhoodRepository, neighborhoodMapper);
        this.neighborhoodRepository = neighborhoodRepository;
        this.familyRepository = familyRepository;
    }


    @Override
    public NeighborhoodResponse create(NeighborhoodRequest request) {
        Neighborhood neighborhood = mapper.fromRequest(request);
        Neighborhood createdNeighborhood = neighborhoodRepository.save(neighborhood);
        return mapper.toResponse(createdNeighborhood);
    }


    @Override
    public NeighborhoodResponse getNeighborhoodByUserId(Long userId) {
        Family family = familyRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Family not found for user id " + userId));
        Neighborhood neighborhood = family.getNeighborhood();
        if(neighborhood == null) {
            throw new ResourceNotFoundException(
                    "Neighborhood not found for family of user id " + userId);
        }
            return mapper.toResponse(neighborhood);
        }

    @Override
    public Page<NeighborhoodResponse> getNeighborhoods(Pageable pageable) {
        return neighborhoodRepository.findAll(pageable)
                .map(mapper::toResponse);
    }


    @Override
    protected void updateEntityFromRequest(NeighborhoodRequest request, Neighborhood existing) {
       existing.setCityName(request.getCityName());
       existing.setName(request.getName());
       existing.setStreetName(request.getStreetName());
    }

}

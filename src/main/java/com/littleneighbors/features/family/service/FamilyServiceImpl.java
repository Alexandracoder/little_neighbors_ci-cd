package com.littleneighbors.features.family.service;

import com.littleneighbors.features.child.repository.ChildRepository;
import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.mapper.FamilyMapper;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.family.repository.FamilyRepository;
import com.littleneighbors.features.neighborhood.model.Neighborhood;
import com.littleneighbors.features.neighborhood.repository.NeighborhoodRepository;
import com.littleneighbors.features.user.repository.UserRepository;
import com.littleneighbors.shared.AbstractGenericService;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FamilyServiceImpl
        extends AbstractGenericService<Family, FamilyRequest, FamilyResponse, Long>
        implements FamilyService {

    private final FamilyRepository familyRepository;
    private final NeighborhoodRepository neighborhoodRepository;
    private final UserRepository userRepository;
    private final ChildRepository childRepository;
    private final FamilyMapper mapper;

    public FamilyServiceImpl(FamilyRepository familyRepository, NeighborhoodRepository neighborhoodRepository,
    UserRepository userRepository,ChildRepository childRepository, FamilyMapper mapper) {
        super(familyRepository, mapper);
        this.familyRepository = familyRepository;
        this.neighborhoodRepository = neighborhoodRepository;
        this.userRepository = userRepository;
        this.childRepository = childRepository;
        this.mapper = mapper;
    }

    @Override
    public FamilyResponse create(FamilyRequest request) {
        Family family = mapper.fromRequest(request);

        family.setUser(
                userRepository.findById(request.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + request.getUserId()))
        );

        family.setNeighborhood(
                neighborhoodRepository.findById(request.getNeighborhoodId())
                        .orElseThrow(() -> new ResourceNotFoundException("Neighborhood not found with id " + request.getNeighborhoodId()))
        );
        family.setDescription(request.getDescription());

        return mapper.toResponse(familyRepository.save(family));
    }

    @Override
    public FamilyResponse getFamilyByUserId(Long userId) {
        Family family = familyRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Family not found for user id " + userId));
        return mapper.toResponse(family);
    }

    @Override
    public Page<FamilyResponse> getFamilies(Pageable pageable) {
        return familyRepository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    protected void updateEntityFromRequest(FamilyRequest request, Family existing) {
        existing.setRepresentativeName(request.getRepresentativeName());
        Neighborhood neighborhood = neighborhoodRepository.findById(request.getNeighborhoodId())
                        .orElseThrow(() -> new ResourceNotFoundException("Neighborhood not found with id " + request.getNeighborhoodId()));
        existing.setNeighborhood(neighborhood);
    }

}

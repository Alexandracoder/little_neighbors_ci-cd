package com.littleneighbors.features.family.service;

import com.littleneighbors.features.child.repository.ChildRepository;
import com.littleneighbors.features.family.dto.*;
import com.littleneighbors.features.family.mapper.FamilyMapper;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.family.repository.FamilyRepository;
import com.littleneighbors.features.neighborhood.model.Neighborhood;
import com.littleneighbors.features.neighborhood.repository.NeighborhoodRepository;
import com.littleneighbors.features.user.model.User;
import com.littleneighbors.features.user.repository.UserRepository;
import com.littleneighbors.shared.AbstractGenericService;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import com.littleneighbors.shared.exceptions.UserAlreadyHasFamily;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FamilyServiceImpl
        extends AbstractGenericService<Family, FamilyRequest, FamilyResponse, Long>
        implements FamilyService {

    private final FamilyRepository familyRepository;
    private final NeighborhoodRepository neighborhoodRepository;
    private final UserRepository userRepository;
    private final ChildRepository childRepository;
    private final FamilyMapper mapper;

    public FamilyServiceImpl(FamilyRepository familyRepository,
                             NeighborhoodRepository neighborhoodRepository,
                             UserRepository userRepository,
                             ChildRepository childRepository,
                             FamilyMapper mapper) {
        super(familyRepository, mapper);
        this.familyRepository = familyRepository;
        this.neighborhoodRepository = neighborhoodRepository;
        this.userRepository = userRepository;
        this.childRepository = childRepository;
        this.mapper = mapper;
    }

    @Override
    public FamilyResponse create(FamilyRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + request.getUserId()));

        if (familyRepository.existsByUserId(user.getId())) {
            throw new UserAlreadyHasFamily(request.getUserId());
        }

        Neighborhood neighborhood = neighborhoodRepository.findById(request.getNeighborhoodId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Neighborhood not found with id " + request.getNeighborhoodId()));

        Family family = mapper.fromRequest(request);
        family.setUser(user);
        family.setNeighborhood(neighborhood);

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
    public Optional<Family> findByUserUsername(String username) {
        return familyRepository.findByUser_Email(username);
    }

    @Override
    public FamilyResponse getFamilyByUsername(String email) {
        Family family = familyRepository.findByUser_Email(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Family not found for user with email: " + email));
        return mapper.toResponse(family);
    }
    @Override
    public List<Family> findByNeighborhoodOrPostalCode(Neighborhood neighborhood, String postalCode) {
        return familyRepository.findByNeighborhoodOrNeighborhood_PostalCode(neighborhood, postalCode);
    }

    @Override
    public Page<FamilyResponse> getFamilies(Pageable pageable) {
        return familyRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    protected void updateEntityFromRequest(FamilyRequest request, Family existing) {
        existing.setRepresentativeName(request.getRepresentativeName());
        Neighborhood neighborhood = neighborhoodRepository.findById(request.getNeighborhoodId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Neighborhood not found with id " + request.getNeighborhoodId()));
        existing.setNeighborhood(neighborhood);
    }

    @Override
    public FamilyResponse updateFamily(Long id, FamilyRequest request) {
        Family existing = familyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found with id " + id));
        updateEntityFromRequest(request, existing);
        return mapper.toResponse(familyRepository.save(existing));
    }

    @Override
    public FamilyLocationResponse getLocation(Long familyId) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found"));

        Neighborhood neighborhood = family.getNeighborhood();
        return FamilyLocationResponse.builder()
                .neighborhoodName(neighborhood != null ? neighborhood.getName() : null)
                .postalCode(neighborhood != null ? neighborhood.getPostalCode() : null)
                .build();
    }

    @Override
    public FamilyLocationResponse updateLocation(Long familyId, FamilyLocationRequest request) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found"));

        Neighborhood neighborhood = neighborhoodRepository.findById(request.getNeighborhoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Neighborhood not found"));

        family.setNeighborhood(neighborhood);
        familyRepository.save(family);

        return FamilyLocationResponse.builder()
                .neighborhoodName(neighborhood.getName())
                .postalCode(neighborhood.getPostalCode())
                .build();
    }

    @Override
    public FamilyResponse updateFamilyLocation(Long userId, FamilyLocationRequest request) {
        Family family = familyRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found"));

        Neighborhood neighborhood = neighborhoodRepository.findById(request.getNeighborhoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Neighborhood not found"));

        family.setNeighborhood(neighborhood);
        return mapper.toResponse(familyRepository.save(family));
    }
}

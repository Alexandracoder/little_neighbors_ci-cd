package com.littleneighbors.features.family.service;

import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.mapper.FamilyMapper;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.family.repository.FamilyRepository;
import com.littleneighbors.features.neighborhood.model.Neighborhood;
import com.littleneighbors.features.neighborhood.repository.NeighborhoodRepository;
import com.littleneighbors.features.user.exception.DuplicateResourceException;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;
    private final NeighborhoodRepository neighborhoodRepository;
    private final FamilyMapper familyMapper;

    @Override
    public FamilyResponse createFamily(FamilyRequest request) {
        boolean exists = familyRepository.existsByUserId(request.getUserId());
        if (exists) {
            throw new DuplicateResourceException(
                    "This user already has a family created");
        }

        Family family = FamilyMapper.toEntity(request);

        Family savedFamily = familyRepository.save(family);

        return FamilyMapper.toResponse(savedFamily);
    }

    @Override
    public FamilyResponse getFamilyById(Long id) {
        Family family = familyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found with id: " + id));
        return FamilyMapper.toResponse(family);
    }

    @Override
    public List<FamilyResponse> getAllFamilies() {
        return familyRepository.findAll()
                .stream()
                .map(FamilyMapper::toResponse)
                .toList();
    }

    @Override
    public FamilyResponse updateFamily( Long id, FamilyRequest request) {
        Family family = familyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found with id: " + id));

        Neighborhood neighborhood = neighborhoodRepository.findByName(request.getNeighborhood())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Neighborhood not found: " + request.getNeighborhood()));
        family.setRepresentativeName(request.getRepresentativeName());
        family.setNeighborhood(neighborhood);
        family.setArea(request.getArea());

        Family updatedFamily = familyRepository.save(family);

        return FamilyMapper.toResponse(updatedFamily);
    }

    @Override
    public void deleteFamily(Long id) {
        if(!familyRepository.existsById(id))
            throw new ResourceNotFoundException("No family exists with ID " + id + ", cannot delete.");
    }
}

package com.littleneighbors.features.family.service;

import com.littleneighbors.features.child.model.Child;
import com.littleneighbors.features.child.repository.ChildRepository;
import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.mapper.FamilyMapper;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.family.repository.FamilyRepository;
import com.littleneighbors.features.neighborhood.repository.NeighborhoodRepository;
import com.littleneighbors.features.user.model.User;
import com.littleneighbors.features.user.repository.UserRepository;
import com.littleneighbors.features.user.exception.DuplicateResourceException;
import com.littleneighbors.shared.AbstractGenericService;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Transactional
    public FamilyResponse create(@NotNull FamilyRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (familyRepository.existsByUserId(user.getId())) {
            throw new DuplicateResourceException("This user already has a family created");
        }

        Family family = mapper.fromRequest(request);
        family.setUser(user);
        family.setNeighborhood(
                neighborhoodRepository.findById(request.getNeighborhoodId())
                        .orElseThrow(() -> new ResourceNotFoundException("Neighborhood not found"))
        );

        if (request.getChildrenIds() != null && !request.getChildrenIds().isEmpty()) {
            List<Child> children = childRepository.findAllById(request.getChildrenIds());
            if (children.size() != request.getChildrenIds().size()) {
                throw new ResourceNotFoundException("One or more children not found");
            }
            children.forEach(child -> child.setFamily(family));
            family.setChildren(children);
        }

        Family savedFamily = familyRepository.save(family);
        return mapper.toResponse(savedFamily);
    }

    @Override
    @Transactional
    public FamilyResponse update(Long id, FamilyRequest request) {
        Family family = familyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (familyRepository.existsByUserId(user.getId()) &&
                !family.getUser().getId().equals(user.getId())) {
            throw new DuplicateResourceException("This user already has a family created");
        }

        family.setRepresentativeName(request.getRepresentativeName());
        family.setArea(request.getArea());
        family.setNeighborhood(
                neighborhoodRepository.findById(request.getNeighborhoodId())
                        .orElseThrow(() -> new ResourceNotFoundException("Neighborhood not found"))
        );

        if (request.getChildrenIds() != null) {
            List<Child> children = childRepository.findAllById(request.getChildrenIds());
            children.forEach(child -> child.setFamily(family));
            family.setChildren(children);
        }

        Family updatedFamily = familyRepository.save(family);
        return mapper.toResponse(updatedFamily);
    }

    @Override
    public Page<FamilyResponse> getFamilies(Pageable pageable) {
        return familyRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public FamilyResponse getFamilyByUserId(Long userId) {
        Family family = familyRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found for user id " + userId));
        return mapper.toResponse(family);
    }
}
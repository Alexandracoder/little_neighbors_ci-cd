package com.littleneighbors.features.child.service;

import com.littleneighbors.features.child.dto.ChildRequest;
import com.littleneighbors.features.child.dto.ChildResponse;
import com.littleneighbors.features.child.mapper.ChildMapper;
import com.littleneighbors.features.child.entity.Child;
import com.littleneighbors.features.child.repository.ChildRepository;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.entity.Family;
import com.littleneighbors.features.family.repository.FamilyRepository;
import com.littleneighbors.shared.AbstractGenericService;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChildServiceImpl
        extends AbstractGenericService<Child, ChildRequest, ChildResponse, Long>
        implements ChildService {

    private final ChildRepository childRepository;
    private final FamilyRepository familyRepository;
    private final ChildMapper childMapper;

    public ChildServiceImpl(
            ChildRepository childRepository,
            FamilyRepository familyRepository,
            ChildMapper childMapper
    ) {
        super(childRepository, childMapper);
        this.childRepository = childRepository;
        this.familyRepository = familyRepository;
        this.childMapper = childMapper;

    }

    @Override
    @Transactional
    public ChildResponse addChildToFamily(Long familyId, ChildRequest request) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found with id " + familyId));

        Child child = childMapper.fromRequest(request);
        child.setFamily(family);

        // Temporalmente mientrás implemento seguridad y el user viene de service.
        Long tempUserId = 1L;
        child.setUserId(tempUserId);

        Child savedChild = childRepository.save(child);
        return childMapper.toResponse(savedChild);
    }


    @Override
    public Page<ChildResponse> getChildrenByFamily(Long familyId, Pageable pageable) {
        return childRepository.findByFamilyId(familyId, pageable)
                .map(childMapper::toResponse);
    }

    @Override
    public FamilyResponse getFamilyByUsername(String username) {
        return null;
    }

    @Override
    protected void updateEntityFromRequest(ChildRequest request, Child existing) {

        existing.setGender((request.getGender()));
        existing.setBirthDate(request.getBirthDate());
    }
}



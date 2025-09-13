package com.littleneighbors.features.interest.service;

import com.littleneighbors.features.child.entity.Child;
import com.littleneighbors.features.child.repository.ChildRepository;
import com.littleneighbors.features.interest.dto.InterestRequest;
import com.littleneighbors.features.interest.dto.InterestResponse;
import com.littleneighbors.features.interest.mapper.InterestMapper;
import com.littleneighbors.features.interest.entity.Interest;
import com.littleneighbors.features.interest.repository.InterestRepository;
import com.littleneighbors.shared.AbstractGenericService;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InterestServiceImpl extends AbstractGenericService<Interest, InterestRequest, InterestResponse, Long> {

    private  final ChildRepository childRepository;
    public  InterestServiceImpl(InterestRepository repository, InterestMapper mapper, ChildRepository childRepository) {
        super(repository, mapper);
        this.childRepository = childRepository;
    }
    @Override
    protected void updateEntityFromRequest(InterestRequest request, Interest existing ) {
        existing.setName(request.getName());
        existing.setType(request.getType());
    }

    public List<InterestResponse> getInterestByChild(Long childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ResourceNotFoundException("Child not found with id" + childId));
        return child.getInterests()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}

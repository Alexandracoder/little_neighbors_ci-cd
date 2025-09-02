package com.littleneighbors.features.interest.service;

import com.littleneighbors.features.child.repository.ChildRepository;
import com.littleneighbors.features.interest.dto.InterestRequest;
import com.littleneighbors.features.interest.dto.InterestResponse;
import com.littleneighbors.features.interest.mapper.InterestMapper;
import com.littleneighbors.features.interest.model.Interest;
import com.littleneighbors.features.interest.repository.InterestRepository;
import com.littleneighbors.shared.AbstractGenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl extends AbstractGenericService<Interest, InterestRequest, InterestResponse, Long> implements {

    private  final ChildRepository childRepository;
    public  InterestServiceImpl(InterestRepository repository, InterestMapper mapper, ChildRepository childRepository) {
        super(repository, mapper);
        this.childRepository = childRepository;
    }

}

package com.littleneighbors.features.interest.mapper;

import com.littleneighbors.features.interest.dto.InterestRequest;
import com.littleneighbors.features.interest.dto.InterestResponse;
import com.littleneighbors.features.interest.model.Interest;
import com.littleneighbors.shared.mapper.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class InterestMapper implements GenericMapper<InterestRequest, InterestResponse, Interest> {

    @Override
    public Interest fromRequest(InterestRequest request) {
        return Interest.builder()
                .name(request.getName())
                .build();
    }

    @Override
    public InterestResponse toResponse(Interest entity) {
        return new InterestResponse(entity.getId(), entity.getName());
    }
}

package com.littleneighbors.features.interest.mapper;

import com.littleneighbors.features.interest.dto.InterestRequest;
import com.littleneighbors.features.interest.dto.InterestResponse;
import com.littleneighbors.features.interest.entity.Interest;
import com.littleneighbors.shared.mapper.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class InterestMapper extends GenericMapper<InterestRequest, InterestResponse, Interest> {

    @Override
    public Interest fromRequest(InterestRequest request) {
        return Interest.builder()
                .name(request.getName())
                .type(request.getType())

                .build();
    }

    @Override
    public InterestResponse toResponse(Interest entity) {
        return new InterestResponse(
                entity.getId(),
                entity.getName(),
                entity.getType()
        );

    }
}

package com.littleneighbors.features.match.mapper;


import com.littleneighbors.features.match.dto.MatchRequest;
import com.littleneighbors.features.match.dto.MatchResponse;
import com.littleneighbors.features.match.model.Match;
import com.littleneighbors.features.match.model.MatchStatus;
import com.littleneighbors.shared.mapper.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper extends GenericMapper<MatchRequest, MatchResponse, Match> {
    @Override
    public Match fromRequest(MatchRequest request) {
        return Match.builder()
                .status(MatchStatus.PENDING)
                .build();
    }
    @Override
    public MatchResponse toResponse(Match entity) {
        return MatchResponse.builder()
                .id(entity.getId())
                .requesterChildId(entity.getRequester().getId())
                .receiverChildId(entity.getReceiver().getId())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

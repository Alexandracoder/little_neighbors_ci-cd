package com.littleneighbors.features.match.service;

import com.littleneighbors.features.match.dto.MatchRequest;
import com.littleneighbors.features.match.dto.MatchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatchService {
    MatchResponse createMatch(MatchRequest request);
    Page<MatchResponse> getMatchesForChild(Long childId, Pageable pageable);
    MatchResponse acceptMatch(Long matchId);
    MatchResponse rejectMatch(Long matchId);
}

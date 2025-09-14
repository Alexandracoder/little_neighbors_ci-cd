package com.littleneighbors.features.match.controller;

import com.littleneighbors.features.match.dto.MatchResponse;
import com.littleneighbors.features.match.service.MatchServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchServiceImpl matchService;

    public MatchController(MatchServiceImpl matchService) {
        this.matchService = matchService;
    }
    /**
     * Get compatible matches for a family and generate pending matches.
     *
     * @param familyId the ID of the family to find matches for
     * @param pageable pagination information
     * @return a page of MatchResponse containing compatible matches
     */
    @GetMapping("/compatible/{familyId}")
    public ResponseEntity<Page<MatchResponse>> getCompatibleMatches(
            @PathVariable Long familyId,
            Pageable pageable
    ) {
        Page<MatchResponse> matches = matchService.findCompatibleMatches(familyId, pageable);
        return ResponseEntity.ok(matches);

    }
    @PostMapping("/{matchId}/accept")
    public ResponseEntity<MatchResponse> acceptMatch(@PathVariable Long matchId) {
        MatchResponse response = matchService.acceptMatch(matchId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{matchId}/reject")
    public ResponseEntity<MatchResponse> rejectMatch(@PathVariable Long matchId) {
        MatchResponse response = matchService.rejectMatch(matchId);
        return ResponseEntity.ok(response);
    }

}


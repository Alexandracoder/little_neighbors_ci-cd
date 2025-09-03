package com.littleneighbors.features.match.controller;

import com.littleneighbors.features.match.dto.MatchResponse;
import com.littleneighbors.features.match.service.MatchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MatchController {

    private final MatchServiceImpl matchService;

    public MatchController(MatchServiceImpl matchService) {
        this.matchService = matchService;
    }
    /**
     * @param
     * @param
     * @return
     */
    @GetMapping("/compatible/{familyId}")
    public ResponseEntity<Page<MatchResponse>> getCompatibleMatches(
            @PathVariable Long familyId,
            Pageable pageable
    ) {
        Page<MatchResponse> matches = matchService.findCompatibleMatches(familyId, pageable);
        return ResponseEntity.ok(matches);

    }
    @PostMapping ("/{matchId}/accept")
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


package com.littleneighbors.features.match.service;

import com.littleneighbors.features.child.entity.Child;
import com.littleneighbors.features.family.entity.Family;
import com.littleneighbors.features.family.repository.FamilyRepository;
import com.littleneighbors.features.match.dto.MatchRequest;
import com.littleneighbors.features.match.dto.MatchResponse;
import com.littleneighbors.features.match.mapper.MatchMapper;
import com.littleneighbors.features.match.entity.Match;
import com.littleneighbors.features.match.entity.MatchStatus;
import com.littleneighbors.features.match.repository.MatchRepository;
import com.littleneighbors.shared.AbstractGenericService;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class MatchServiceImpl extends AbstractGenericService<Match, MatchRequest, MatchResponse, Long> {

    private final MatchRepository matchRepository;
    private final FamilyRepository familyRepository;
    private final MatchMapper mapper;



    @Override
    protected void updateEntityFromRequest(MatchRequest request, Match existing) {
    }

    public MatchResponse acceptMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found"));
        match.setStatus(MatchStatus.ACCEPTED);
        return mapper.toResponse(matchRepository.save(match));
    }

    public MatchResponse rejectMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found"));
        match.setStatus(MatchStatus.REJECTED);
        return mapper.toResponse(matchRepository.save(match));
    }

    public Page<MatchResponse> findCompatibleMatches(Long familyId, Pageable pageable) {
        Family currentFamily = familyRepository.findById(familyId)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found"));

        List<Family> nearbyFamilies = findNearbyFamilies(currentFamily).stream()
                .filter(f -> !f.getId().equals(familyId))
                .collect(Collectors.toList());

        List<Family> compatibleFamilies = nearbyFamilies.stream()
                .filter(f -> hasCompatibleChildren(currentFamily, f))
                .filter(f -> !alreadyMatchedThisWeek(currentFamily, f))
                .collect(Collectors.toList());

        List<MatchResponse> responses = compatibleFamilies.stream()
                .map(f -> createPendingMatch(currentFamily, f))
                .filter(r -> r != null)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), responses.size());
        return new PageImpl<>(responses.subList(start, end), pageable, responses.size());
    }

    private boolean hasCompatibleChildren(Family f1, Family f2) {
        for (Child c1 : f1.getChildren()) {
            for (Child c2 : f2.getChildren()) {
                int ageDiff = Math.abs(c1.getAge() - c2.getAge());
                if (ageDiff <= 2 && hasCommonInterests(c1, c2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasCommonInterests(Child c1, Child c2) {
        if (c1.getInterests() == null || c2.getInterests() == null) return false;
        return c1.getInterests().stream()
                .anyMatch(i -> c2.getInterests().contains(i));
    }

    private boolean alreadyMatchedThisWeek(Family f1, Family f2) {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        return matchRepository.existsByRequester_FamilyAndReceiver_FamilyAndCreatedAtAfter(f1, f2, oneWeekAgo)
                || matchRepository.existsByReceiver_FamilyAndRequester_FamilyAndCreatedAtAfter(f1, f2, oneWeekAgo);
    }

    private List<Family> findNearbyFamilies(Family currentFamily) {
        return familyRepository.findByNeighborhoodOrNeighborhood_PostalCode(
                currentFamily.getNeighborhood(),
                currentFamily.getNeighborhood().getPostalCode()
        );

    }
    protected record ChildPair(Child c1, Child c2, int score) {}

    private MatchResponse createPendingMatch(Family requesterFamily, Family receiverFamily) {
        List<ChildPair> pairs = findCompatiblePairsRanked(requesterFamily, receiverFamily);

        if (pairs.isEmpty()) {
            return null;
        }
        ChildPair bestPair = pairs.get(0);

        Match match = Match.builder()
                .requester(bestPair.c1())
                .receiver(bestPair.c2())
                .status(MatchStatus.PENDING)
                .build();
        matchRepository.save(match);
        return mapper.toResponse(match);
    }

    private List<ChildPair> findCompatiblePairsRanked(Family f1, Family f2) {
        return f1.getChildren().stream()
                .flatMap(c1 -> f2.getChildren().stream()
                        .map(c2 -> new ChildPair(c1, c2, scorePair(c1, c2))))
                        .filter(pair -> pair.score > Integer.MIN_VALUE)
                        .sorted((a, b) -> Integer.compare(b.score, a.score))
                        .toList();
    }

    private int scorePair(Child c1, Child c2) {
        int score = 0;
        int ageDiff = Math.abs(c1.getAge() - c2.getAge());
        if (ageDiff <= 2) score+= 10;
        if(hasCommonInterests(c1, c2)) score += 5;
        return score;
    }
}



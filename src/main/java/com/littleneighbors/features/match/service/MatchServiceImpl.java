package com.littleneighbors.features.match.service;

import com.littleneighbors.features.child.model.Child;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.family.repository.FamilyRepository;
import com.littleneighbors.features.match.dto.MatchRequest;
import com.littleneighbors.features.match.dto.MatchResponse;
import com.littleneighbors.features.match.mapper.MatchMapper;
import com.littleneighbors.features.match.model.Match;
import com.littleneighbors.features.match.model.MatchStatus;
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
import java.util.Optional;
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
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }
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
        return matchRepository.existsByRequesterInAndReceiverInAndCreatedAfter(
                f1.getChildren(), f2.getChildren(), oneWeekAgo
        );
    }

    private List<Family> findNearbyFamilies(Family currentFamily) {
        return familyRepository.findByNeighborhoodOrPostalCode(
                currentFamily.getNeighborhood(),
                currentFamily.getNeighborhood().getPostalCode() // asegúrate de que existe getter
        );
    }

    private MatchResponse createPendingMatch(Family requesterFamily, Family receiverFamily) {
        Optional<Child> requesterChildOpt = requesterFamily.getChildren().stream().findFirst();
        Optional<Child> receiverChildOpt = receiverFamily.getChildren().stream().findFirst();

        if (requesterChildOpt.isEmpty() || receiverChildOpt.isEmpty()) {
            throw new IllegalStateException("Both families must have at least one child");
        }

        Match match = Match.builder()
                .requester(requesterChildOpt.get())
                .receiver(receiverChildOpt.get())
                .status(MatchStatus.PENDING)
                .build();

        matchRepository.save(match);
        return mapper.toResponse(match);
    }
}

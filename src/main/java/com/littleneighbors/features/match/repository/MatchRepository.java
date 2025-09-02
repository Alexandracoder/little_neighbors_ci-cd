package com.littleneighbors.features.match.repository;

import com.littleneighbors.features.child.model.Child;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.match.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    Page<Match>findByRequesterIdOrReceiverId(Long requesterId, Long receiverId, Pageable pageable);
    boolean existsByRequesterAndReceiverAndCreatedAtAfter(Family requester, Family receiver, LocalDateTime after);
    boolean existsByReceiverAndRequesterAndCreatedAfter(Family receiver, Family requester, LocalDateTime after);
    boolean existsByRequesterInAndReceiverInAndCreatedAfter(List<Child> requesterChildren, List<Child> receiverChildren, LocalDateTime after);
    List<Match> findByRequesterAndReceiverOrReceiverAndRequester(
            Family requester, Family receiver,
            Family receiver2, Family requester2
    );

}

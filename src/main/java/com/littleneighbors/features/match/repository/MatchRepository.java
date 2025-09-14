package com.littleneighbors.features.match.repository;

import com.littleneighbors.features.child.entity.Child;
import com.littleneighbors.features.family.entity.Family;
import com.littleneighbors.features.match.entity.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Page<Match> findByRequesterIdOrReceiverId(Long requesterId, Long receiverId, Pageable pageable);

    boolean existsByRequesterAndReceiverAndCreatedAtAfter(Child requester, Child receiver, LocalDateTime after);

    boolean existsByReceiverAndRequesterAndCreatedAtAfter(Child receiver, Child requester, LocalDateTime after);


    @Query("""
        SELECT m FROM Match m
        WHERE (m.requester = :requester AND m.receiver = :receiver)
           OR (m.requester = :receiver AND m.receiver = :requester)
    """)
    List<Match> findByRequesterAndReceiverOrReceiverAndRequester(
            @Param("requester") Child requester,
            @Param("receiver") Child receiver
    );

    boolean existsByRequester_FamilyAndReceiver_FamilyAndCreatedAtAfter(
            Family requesterFamily,
            Family receiverFamily,
            LocalDateTime after
    );

    boolean existsByReceiver_FamilyAndRequester_FamilyAndCreatedAtAfter(
            Family requesterFamily,
            Family receiverFamily,
            LocalDateTime after
    );

    @Query("""
        SELECT m FROM Match m
        WHERE (m.requester.family = :requesterFamily AND m.receiver.family = :receiverFamily)
           OR (m.requester.family = :receiverFamily AND m.receiver.family = :requesterFamily)
    """)
    List<Match> findByRequesterFamilyAndReceiverFamilyOrReceiverFamilyAndRequesterFamily(
            @Param("requesterFamily") Family requesterFamily,
            @Param("receiverFamily") Family receiverFamily
    );

}

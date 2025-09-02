package com.littleneighbors.features.match.repository;

import com.littleneighbors.features.child.model.Child;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.match.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends JpaRepository<Match, Long> {

    // =======================
    // Matches por Child
    // =======================

    // Buscar matches donde un niño es requester o receiver
    Page<Match> findByRequesterIdOrReceiverId(Long requesterId, Long receiverId, Pageable pageable);

    // Ver si existe un match entre dos niños después de una fecha
    boolean existsByRequesterAndReceiverAndCreatedAtAfter(Child requester, Child receiver, LocalDateTime after);

    boolean existsByReceiverAndRequesterAndCreatedAtAfter(Child receiver, Child requester, LocalDateTime after);

    // Ver si existe un match entre listas de niños
    boolean existsByRequesterInAndReceiverInAndCreatedAtAfter(List<Child> requesterChildren, List<Child> receiverChildren, LocalDateTime after);

    // Buscar matches considerando ambos sentidos (requester-receiver y receiver-requester)
    @Query("""
        SELECT m FROM Match m
        WHERE (m.requester = :requester AND m.receiver = :receiver)
           OR (m.requester = :receiver AND m.receiver = :requester)
    """)
    List<Match> findByRequesterAndReceiverOrReceiverAndRequester(
            @Param("requester") Child requester,
            @Param("receiver") Child receiver
    );

    // =======================
    // Matches por Family
    // =======================

    // Ver si existe un match entre familias (cualquier hijo de cada familia) después de una fecha
    boolean existsByRequester_FamilyAndReceiver_FamilyAndCreatedAtAfter(Family requesterFamily, Family receiverFamily, LocalDateTime after);

    boolean existsByReceiver_FamilyAndRequester_FamilyAndCreatedAtAfter(Family receiverFamily, Family requesterFamily, LocalDateTime after);

    // Buscar matches considerando familias y ambos sentidos
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

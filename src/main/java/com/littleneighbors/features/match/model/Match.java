package com.littleneighbors.features.match.model;

import com.littleneighbors.features.child.model.Child;
import com.littleneighbors.shared.Identifiable;
import com.littleneighbors.shared.model.Auditable;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "matches")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match extends Auditable implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_child_id", nullable = false)
    private Child requester;

    @ManyToOne
    @JoinColumn(name = "receiver_child_id", nullable = false)
    private  Child receiver;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @Override
    public Long getId() {
        return id;

    }
}





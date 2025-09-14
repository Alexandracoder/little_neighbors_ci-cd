package com.littleneighbors.features.interest.entity;

import com.littleneighbors.shared.Identifiable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
 public class Interest implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InterestType type;
}

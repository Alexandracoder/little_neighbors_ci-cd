package com.littleneighbors.features.family.entity;

import com.littleneighbors.features.child.entity.Child;
import com.littleneighbors.features.neighborhood.entity.Neighborhood;
import com.littleneighbors.features.user.entity.User;
import com.littleneighbors.shared.Identifiable;
import com.littleneighbors.shared.entity.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "families")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Family extends Auditable implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "representative_name", length = 255)
    private String representativeName;

    @Column(name = "family_name", length = 255)
    private String familyName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "profile_picture_url", length = 255, nullable = true)
    private String profilePictureUrl;

    @ManyToOne
    @JoinColumn(name = "neighborhood_id")
    private Neighborhood neighborhood;

    @Builder.Default
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children = new ArrayList<>();
}

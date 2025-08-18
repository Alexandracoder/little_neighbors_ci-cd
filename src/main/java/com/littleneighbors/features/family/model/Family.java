package com.littleneighbors.features.family.model;

import com.littleneighbors.features.neighborhood.model.Neighborhood;
import com.littleneighbors.features.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "families")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Family {
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

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "profile:pictureUrl", length = 255)
    private String profilePictureUrl;

   @ManyToOne
   @JoinColumn(name = "neighborhood_id")
   private Neighborhood neighborhood;

    @Column(name = "area",nullable = false, length = 255)
    private String area;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

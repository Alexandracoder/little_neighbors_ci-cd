package com.littleneighbors.features.child.model;

import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.interest.model.Interest;
import com.littleneighbors.shared.Identifiable;
import com.littleneighbors.shared.converter.GenderConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;



@Entity
@Table(name = "children")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Child implements Identifiable<Long> {
    public Long getId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false)
    private Family family;

    @ManyToMany
    @JoinTable(name = "child_interests",
            joinColumns = @JoinColumn(name = "child_id"), inverseJoinColumns = @JoinColumn(name = "interest_id"))
    private List<Interest> interests;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public int getAge() {
        if (birthDate == null) return 0;
        return Period.between(this.birthDate, LocalDate.now()).getYears();

    }
}

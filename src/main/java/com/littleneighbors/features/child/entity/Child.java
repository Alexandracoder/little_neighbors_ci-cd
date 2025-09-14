package com.littleneighbors.features.child.entity;

import com.littleneighbors.features.family.entity.Family;
import com.littleneighbors.features.interest.entity.Interest;
import com.littleneighbors.shared.Identifiable;
import com.littleneighbors.shared.converter.GenderConverter;
import com.littleneighbors.shared.entity.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "children")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Child extends Auditable implements Identifiable<Long> {
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

    public int getAge() {
        if (birthDate == null) return 0;
        return Period.between(this.birthDate, LocalDate.now()).getYears();

    }
}

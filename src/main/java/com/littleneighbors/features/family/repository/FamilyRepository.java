package com.littleneighbors.features.family.repository;

import com.littleneighbors.features.family.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository extends JpaRepository <Family ,Long> {
        Optional<Family>findByUserId(Long userId);
        Optional<Family>findByRepresentativeName(String representativeName);
        List<Family> findByRepresentativeNameContainingIgnoreCase(String  partialName);
//      List<Family>findByNeighborhoodId(Long neighborhoodId);
        Optional<Family>findByFamilyName(String familyName);
        List<Family>findByFamilyNameContainingIgnoreCase(String partialFamilyName);

    boolean existsByUserId(Long userId);
}

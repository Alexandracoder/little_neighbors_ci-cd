package com.littleneighbors.features.family.repository;

import com.littleneighbors.features.family.entity.Family;
import com.littleneighbors.features.neighborhood.entity.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Long> {

    Optional<Family> findByUserId(Long userId);

    Optional<Family> findByRepresentativeName(String representativeName);

    List<Family> findByRepresentativeNameContainingIgnoreCase(String partialName);

    List<Family> findByNeighborhoodId(Long neighborhoodId);

    Optional<Family> findByFamilyName(String familyName);


    Optional<Family> findByUser_Email(String email);


    List<Family> findByFamilyNameContainingIgnoreCase(String partialFamilyName);

    List<Family> findByNeighborhoodOrNeighborhood_PostalCode(Neighborhood neighborhood, String postalCode);

    boolean existsByUserId(Long userId);
}
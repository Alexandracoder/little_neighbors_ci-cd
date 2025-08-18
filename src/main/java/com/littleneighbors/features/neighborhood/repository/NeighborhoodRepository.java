package com.littleneighbors.features.neighborhood.repository;

import com.littleneighbors.features.neighborhood.model.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NeighborhoodRepository extends JpaRepository {
    Optional<Neighborhood> findByName(String name);
}

package com.littleneighbors.features.interest.repository;

import com.littleneighbors.features.interest.entity.Interest;
import com.littleneighbors.features.interest.entity.InterestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    Optional<Interest> findByName(String name);
    List<Interest> findByType(InterestType type);
    boolean existsByName(String name);
}

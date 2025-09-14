package com.littleneighbors.features.interest.service;

import com.littleneighbors.features.interest.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestService extends JpaRepository<Interest, Long> {
}

package com.littleneighbors.features.child.repository;

import com.littleneighbors.features.child.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {
}

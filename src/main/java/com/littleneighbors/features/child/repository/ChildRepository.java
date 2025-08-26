package com.littleneighbors.features.child.repository;

import com.littleneighbors.features.child.model.Child;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {
    Page<Child> findByFamilyId(Long familyId, Pageable pageable);
}

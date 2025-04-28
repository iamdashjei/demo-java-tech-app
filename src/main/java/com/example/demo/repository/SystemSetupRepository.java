package com.example.demo.repository;

import com.example.demo.model.SystemSetup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSetupRepository extends JpaRepository<SystemSetup, Long> {
    // CRUD Operations

    // Pagination
    Page<SystemSetup> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

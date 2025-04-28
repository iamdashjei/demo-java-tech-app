package com.example.demo.repository;

import com.example.demo.model.Consumer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    // CRUD Operations

    // Pagination
    Page<Consumer> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

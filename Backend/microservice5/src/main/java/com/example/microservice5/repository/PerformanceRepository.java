package com.example.microservice5.repository;

import com.example.microservice5.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository  extends JpaRepository<Performance, Long> {
}

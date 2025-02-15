package com.example.microservice5.repository;

import com.example.microservice5.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByEmployeeId(Long employeeId);
}

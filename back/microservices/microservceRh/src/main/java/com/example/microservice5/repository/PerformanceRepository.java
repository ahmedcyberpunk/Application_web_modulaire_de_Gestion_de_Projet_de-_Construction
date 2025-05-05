package com.example.microservice5.repository;

import com.example.microservice5.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PerformanceRepository  extends JpaRepository<Performance, Long> {

    List<Performance> findByEmployeeId(Long employeeId);
    List<Performance> findByEmployeeIdAndDateEvaluationBetween(Long employeeId, LocalDate start, LocalDate end);
    Performance findByEmployeeId(int employeeId);
    @Query("SELECT p.id AS id, p.note AS note, p.dateEvaluation AS dateEvaluation, p.commentaire AS commentaire, e.nom AS employeeNom, e.prenom AS employeePrenom FROM Performance p JOIN p.employee e")
    List<Object[]> findPerformancesWithEmployeeName();




}

package com.example.microservice5.service;


import com.example.microservice5.entity.Employee;
import com.example.microservice5.entity.Performance;
import com.example.microservice5.repository.AbsenceRepository;
import com.example.microservice5.repository.EmployeeRepository;
import com.example.microservice5.repository.PerformanceRepository;
import com.example.microservice5.repository.SalaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PerfermonceService implements IPerfermonceService {
    SalaryRepository salaryRepository;
    AbsenceRepository absenceRepository;
    EmployeeRepository employeeRepository;
    PerformanceRepository performanceRepository;
/*

        public Performance addPerformance(Performance performance) {
            return performanceRepository.save(performance);
        }

    // Récupérer une performance par ID
        public Performance getPerformance(Long id) {
            return performanceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Performance not found"));
        }

    // Récupérer toutes les performances
        public List<Performance> getAllPerformances() {
            return performanceRepository.findAll();
        }


        public Performance updatePerformance(Performance performance) {
            if (performanceRepository.existsById(performance.getId())) {
                return performanceRepository.save(performance);
            } else {
                throw new RuntimeException("Performance not found");
            }
        }

        public void deletePerformance(Long id) {
            if (performanceRepository.existsById(id)) {
                performanceRepository.deleteById(id);
            } else {
                throw new RuntimeException("Performance not found");
            }
        }

 */


    public Performance affecterPerformance(Long employeeId, Performance performance) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        performance.setEmployee(employee);
        return performanceRepository.save(performance);
    }

    public Performance modifierPerformance(Long performanceId, Performance performance) {
        Performance existingPerformance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new RuntimeException("Performance not found"));
        existingPerformance.setNote(performance.getNote());
        existingPerformance.setDateEvaluation(performance.getDateEvaluation());
        existingPerformance.setCommentaire(performance.getCommentaire());
        return performanceRepository.save(existingPerformance);
    }

    public List<Performance> getPerformancesByEmployeeId(Long employeeId) {
        return performanceRepository.findByEmployeeId(employeeId);
    }
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    public void desaffecterPerformance(Long performanceId) {
        performanceRepository.deleteById(performanceId);
    }





}


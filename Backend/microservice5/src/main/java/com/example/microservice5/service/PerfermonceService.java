package com.example.microservice5.service;


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
public class PerfermonceService {
    SalaryRepository salaryRepository;
    AbsenceRepository absenceRepository;
    EmployeeRepository employeeRepository;
    PerformanceRepository performanceRepository;


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
    }


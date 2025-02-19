package com.example.microservice5.service;

import com.example.microservice5.entity.Absence;
import com.example.microservice5.entity.AbsenceType;
import com.example.microservice5.entity.Employee;
import com.example.microservice5.repository.AbsenceRepository;
import com.example.microservice5.repository.EmployeeRepository;
import com.example.microservice5.repository.PerformanceRepository;
import com.example.microservice5.repository.SalaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AbsenceService  implements IAbsenceService{
    SalaryRepository salaryRepository;
    AbsenceRepository absenceRepository;
    EmployeeRepository employeeRepository;
    PerformanceRepository performanceRepository;


    public Absence affecterAbsenceEmployee(Absence absence, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        absence.setEmployee(employee);
        return absenceRepository.save(absence);
    }


    public Absence updateAbsenceType(Long id, AbsenceType newType) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence non trouvée"));

        absence.setType(newType);
        return absenceRepository.save(absence);
    }

    //  Récupérer toutes les absences
    public List<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }

    // Récupérer les absences d'un employee
    public List<Absence> getAbsencesByEmployee(Long employeeId) {
        return absenceRepository.findByEmployeeId(employeeId);
    }


    public void deleteAbsence(Long id) {
        if (absenceRepository.existsById(id)) {
            absenceRepository.deleteById(id);
        } else {
            throw new RuntimeException("Absence non trouvée");
        }}
}

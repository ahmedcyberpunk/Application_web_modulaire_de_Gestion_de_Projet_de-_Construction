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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if (absence.getType() == AbsenceType.RETARD
                && (absence.getDureeHeures() == null || absence.getDureeHeures() <= 0)) {
            throw new RuntimeException("La durée en heures est obligatoire pour un retard");
        }

        absence.setEmployee(employee);
        return absenceRepository.save(absence);
    }

    public Absence updateAbsence(Long id, AbsenceType newType, LocalDate newDateDebut, LocalDate newDateFin, Double newDureeHeures) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence non trouvée"));

        // Mise à jour des valeurs
        absence.setType(newType);

        if (newType == AbsenceType.RETARD) {
            absence.setDureeHeures(newDureeHeures);
            absence.setDateDebut(absence.getDateDebut());
            absence.setDateFin(absence.getDateFin());
        } else {
            absence.setDateDebut(newDateDebut);
            absence.setDateFin(newDateFin);
            absence.setDureeHeures(null);
        }

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

    public List<Map<String, Object>> getAllAbsencesWithEmployeeInfo() {
        List<Object[]> results = absenceRepository.findAllAbsencesWithEmployeeInfo();
        List<Map<String, Object>> absences = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> absenceMap = new HashMap<>();
            absenceMap.put("id", row[0]);
            absenceMap.put("dateDebut", row[1]);
            absenceMap.put("dateFin", row[2]);
            absenceMap.put("type", row[3]);
            absenceMap.put("employeeId", row[4]);
            absenceMap.put("nom", row[5]);
            absenceMap.put("prenom", row[6]);
            absenceMap.put("dureeHeures", row[7]);


            absences.add(absenceMap);
        }
        return absences;
    }


}

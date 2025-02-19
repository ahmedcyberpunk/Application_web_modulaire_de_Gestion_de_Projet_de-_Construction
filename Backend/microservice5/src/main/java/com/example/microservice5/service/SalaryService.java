package com.example.microservice5.service;


import com.example.microservice5.entity.Absence;
import com.example.microservice5.entity.Employee;
import com.example.microservice5.entity.Performance;
import com.example.microservice5.entity.Salary;
import com.example.microservice5.repository.AbsenceRepository;
import com.example.microservice5.repository.EmployeeRepository;
import com.example.microservice5.repository.PerformanceRepository;
import com.example.microservice5.repository.SalaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class SalaryService implements ISalaryService {
    private final SalaryRepository salaryRepository;
    private final AbsenceRepository absenceRepository;
    private final EmployeeRepository employeeRepository;
    private final PerformanceRepository performanceRepository;

    public Salary calculateSalary(Long employeeId, LocalDate mois) {
        Employee employee = findEmployeeById(employeeId);
        Long salaireBrut = employee.getSalaire();

        //  Calcul des déductions basées sur les absences
        Long deductions = calculateDeductions(employee, mois);

        // Calcul des primes basées sur la performance
        Long prime = calculatePrime(employeeId, mois);

        // Calcul du salaire net
        Long salaireNet = Math.round(((salaireBrut - deductions)+ prime) * 0.75); // 25% d'impôts

        Salary salary = new Salary(null, mois, salaireBrut, salaireNet, prime, deductions, employee);
        return salaryRepository.save(salary);
    }



    private Long calculateDeductions(Employee employee, LocalDate mois) {
        // Calcul des dates du mois
        LocalDate debutMois = mois.withDayOfMonth(1);
        LocalDate finMois = mois.withDayOfMonth(mois.lengthOfMonth());

        // Récupérer toutes les absences dans le mois pour l'employé
        List<Absence> absences = absenceRepository.findByEmployeeIdAndDateDebutBetween(
                employee.getId(), debutMois, finMois);

        long deduction = 0L;

        // Parcourir les absences pour calculer les déductions
        for (Absence absence : absences) {
            long joursAbsence = absence.getDateFin().toEpochDay() - absence.getDateDebut().toEpochDay() + 1; // +1 pour inclure le jour de fin
            long deductionParJour = (employee.getSalaire() / 30); // Salaire journalier

            // Appliquer les déductions en fonction du type d'absence
            switch (absence.getType()) {
                case SANS_SOLDE:
                    deduction += joursAbsence * deductionParJour; // Déduction complète
                    break;
                case MALADIE:
                    deduction += (joursAbsence * deductionParJour) / 2; // 50% de déduction pour maladie
                    break;
                case RETARD:
                    deduction += (deductionParJour / 4); // Petite déduction pour retard
                    break;
                default:
                    break; // Aucun changement pour les congés payés
            }
        }
        return deduction; // Convertir en long pour le résultat final
    }



    private Long calculatePrime(Long employeeId, LocalDate mois) {
        List<Performance> performances = performanceRepository.findByEmployeeIdAndDateEvaluationBetween(employeeId, mois.withDayOfMonth(1), mois.withDayOfMonth(mois.lengthOfMonth()));
        long prime = 0L;

        for (Performance perf : performances) {
            if (perf.getNote() >= 8) {
                prime += 500L; // Bonus pour bonnes performances
            } else if (perf.getNote() >= 5) {
                prime += 200L; // Prime moyenne
            }
        }

        return prime;
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

}
}
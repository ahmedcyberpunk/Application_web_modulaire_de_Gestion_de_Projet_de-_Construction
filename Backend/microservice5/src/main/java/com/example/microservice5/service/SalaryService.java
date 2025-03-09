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

    @Override
    public Salary calculateSalary(Long employeeId, LocalDate mois) {
        Employee employee = findEmployeeById(employeeId);
        Long salaireBrut = employee.getSalaire();

        // Calcul des déductions pour absences
        Long deductions = calculateDeductions(employee, mois);

        // Calcul des primes (performance et heures supp)
        Long primePerformance = calculatePrimePerformance(employeeId, mois);
        Long primeHeuresSupp = calculatePrimeHeuresSupp(employee);

        // Calcul des cotisations sociales (CNSS + retraite)
        Long cotisationCNSS = Math.round(salaireBrut * 0.0918);
        Long cotisationRetraite = Math.round(salaireBrut * 0.04);
        Long cotisationsSociales = cotisationCNSS + cotisationRetraite;

        // Gestion de l'avance sur salaire avec majoration de 10%
        Long avanceSalaire = (employee.getAvanceSalaire() != null) ? employee.getAvanceSalaire() : 0L;
        Long montantRemboursement = Math.round(avanceSalaire * 1.10); // Majoration de 10%

        // Calcul du salaire imposable
        Long salaireImposable = salaireBrut - cotisationsSociales - deductions + primePerformance + primeHeuresSupp - montantRemboursement;

        // Calcul de l'impôt sur le revenu (IRPP)
        Long impotRevenu = calculateIRPP(salaireImposable);

        // Calcul du salaire net
        Long salaireNet = salaireImposable - impotRevenu;

        // Création et enregistrement du salaire
        Salary salary = new Salary(null, mois, salaireBrut, salaireNet, primePerformance, primeHeuresSupp,
                cotisationCNSS, cotisationRetraite, impotRevenu, salaireImposable,montantRemboursement, deductions, employee);

        salary.setSalaireNet(salaireNet);
        salary.setSalaireBrut(salaireBrut);
        salary.setCotisationCNSS(cotisationCNSS);
        salary.setCotisationRetraite(cotisationRetraite);
        salary.setImpotRevenu(impotRevenu);
        salary.setDeductions(deductions);
        salary.setPrimePerformance(primePerformance);
        salary.setPrimeHeuresSupp(primeHeuresSupp);
        salary.setSalaireImposable(salaireImposable);
        salary.setAvanceSalaire(avanceSalaire);



        return salaryRepository.save(salary);
    }

    // Fonction pour calculer les déductions liées aux absences
    private Long calculateDeductions(Employee employee, LocalDate mois) {
        List<Absence> absences = absenceRepository.findByEmployeeIdAndDateDebutBetween(
                employee.getId(), mois.withDayOfMonth(1), mois.withDayOfMonth(mois.lengthOfMonth()));

        long deduction = 0L;
        long deductionParJour = employee.getSalaire() / 30;

        for (Absence absence : absences) {
            long joursAbsence = absence.getDateFin().toEpochDay() - absence.getDateDebut().toEpochDay() + 1;
            switch (absence.getType()) {
                case SANS_SOLDE:
                    deduction += joursAbsence * deductionParJour;
                    break;
                case MALADIE:
                    deduction += (joursAbsence * deductionParJour) / 2;
                    break;
                case RETARD:
                    deduction += (deductionParJour / 4);
                    break;
                default:
                    break;
            }
        }
        return deduction;
    }

    // Fonction pour calculer la prime de performance
    private Long calculatePrimePerformance(Long employeeId, LocalDate mois) {
        List<Performance> performances = performanceRepository.findByEmployeeIdAndDateEvaluationBetween(
                employeeId, mois.withDayOfMonth(1), mois.withDayOfMonth(mois.lengthOfMonth()));

        long prime = 0L;
        for (Performance perf : performances) {
            if (perf.getNote() >= 8) {
                prime += 80L;
            } else if (perf.getNote() >= 5) {
                prime += 20L;
            }
        }
        return prime;
    }

    // Fonction pour calculer la prime des heures supplémentaires
    private Long calculatePrimeHeuresSupp(Employee employee) {
        long tarifHoraire = employee.getSalaire() / 160; // Moyenne 160h/mois
        return tarifHoraire * employee.getHeuresSupp();
    }

    // Fonction pour calculer l'IRPP selon le barème progressif
    private Long calculateIRPP(Long salaireImposable) {
        if (salaireImposable <= 5000) return Math.round(salaireImposable * 0.00);
        else if (salaireImposable <= 20000) return Math.round((salaireImposable - 5000) * 0.26);
        else return Math.round((salaireImposable - 20000) * 0.35 + (15000 * 0.26));
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
    }

    public List<Salary> getHistoriqueSalaire(Long employeeId) {
        return salaryRepository.findByEmployeeIdOrderByMoisDesc(employeeId);
    }
}

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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

        // Calcul des déductions basées sur les absences
        Long deductions = calculateDeductions(employee, mois);

        // Calcul des primes basées sur la performance
        Long primePerformance = calculatePrimePerformance(employeeId, mois);

        // Calcul de la prime des heures supplémentaires
        Long primeHeuresSupp = calculatePrimeHeuresSupp(employee);

        // Calcul des cotisations sociales (15% du salaire brut)
        Long cotisationsSociales = Math.round(salaireBrut * 0.15); // Par exemple, 15% de cotisations sociales

        Long avanceSalaire = (employee.getAvanceSalaire() != null) ? employee.getAvanceSalaire() : 0L;
        Long reductionAvance = Math.round(avanceSalaire * 0.10);
        Long avanceApresReduction = avanceSalaire + reductionAvance;



        // Calcul du salaire net
        Long salaireImposable = salaireBrut - cotisationsSociales - deductions + primePerformance + primeHeuresSupp - avanceApresReduction;
        Long impotRevenu = Math.round(salaireImposable * 0.25); // 25% d'impôt

        Long salaireNet = salaireImposable - impotRevenu;

        // Créer l'objet Salary avec l'avance sur salaire
        Salary salary = new Salary(null, mois, salaireBrut, salaireNet, deductions, primePerformance, primeHeuresSupp, cotisationsSociales, avanceApresReduction, employee);


        // Enregistrer le salaire dans la base de données
        salary.setSalaireNet(salaireNet);
        salary.setSalaireBrut(salaireBrut);
        salary.setCotisationsSociales(cotisationsSociales);
        salary.setPrimePerformance(primePerformance);
        salary.setPrimeHeuresSupp(primeHeuresSupp);
        salary.setAvanceSalaire(avanceApresReduction);
        salary.setDeductions(deductions);

        return salaryRepository.save(salary);


    }

    private Long calculateDeductions(Employee employee, LocalDate mois) {
        LocalDate debutMois = mois.withDayOfMonth(1);
        LocalDate finMois = mois.withDayOfMonth(mois.lengthOfMonth());
        List<Absence> absences = absenceRepository.findByEmployeeIdAndDateDebutBetween(
                employee.getId(), debutMois, finMois);

        long deduction = 0L;

        for (Absence absence : absences) {
            long joursAbsence = absence.getDateFin().toEpochDay() - absence.getDateDebut().toEpochDay() + 1;
            long deductionParJour = (employee.getSalaire() / 30);

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

        System.out.println("Déduction calculée pour l'employé " + employee.getId() + " : " + deduction);
        return deduction;
    }


    private Long calculatePrimePerformance(Long employeeId, LocalDate mois) {
        List<Performance> performances = performanceRepository.findByEmployeeIdAndDateEvaluationBetween(employeeId, mois.withDayOfMonth(1), mois.withDayOfMonth(mois.lengthOfMonth()));
        long prime = 0L;

        for (Performance perf : performances) {
            if (perf.getNote() >= 8) {
                prime += 80L; // Bonus pour bonnes performances
            } else if (perf.getNote() >= 5) {
                prime += 20L; // Prime moyenne
            }
        }

        return prime;
    }

    private Long calculatePrimeHeuresSupp(Employee employee) {
        // Définir le tarif horaire pour les heures supplémentaires
        long tarifHoraire = employee.getSalaire() / 160; // 160 heures par mois en moyenne

        // Calculer la prime des heures supplémentaires
        return tarifHoraire * employee.getHeuresSupp();
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
    }



}

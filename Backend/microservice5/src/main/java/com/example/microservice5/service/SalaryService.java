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

        // Calcul des déductions basées sur les absences
        Long deductions = calculateDeductions(employee, mois);

        // Calcul des primes basées sur la performance
        Long primePerformance = calculatePrimePerformance(employeeId, mois);

        // Calcul de la prime des heures supplémentaires
        Long primeHeuresSupp = calculatePrimeHeuresSupp(employee);

        // Calcul des cotisations sociales (15% du salaire brut)
        Long cotisationsSociales = Math.round(salaireBrut * 0.15); // Par exemple, 15% de cotisations sociales

        // Récupérer l'avance sur salaire de l'employé
        Long avanceSalaire = employee.getAvanceSalaire();

        Long reductionAvance = Math.round(avanceSalaire * 0.10); // 10% d'augmentation
        Long avanceApresReduction = avanceSalaire + reductionAvance; // Augmenter l'avance


        // Calcul du salaire net
        Long salaireNet = Math.round(((salaireBrut - cotisationsSociales - deductions + primePerformance + primeHeuresSupp) - avanceApresReduction) * 0.75); // 25% d'impôts

        // Créer l'objet Salary avec l'avance sur salaire
        Salary salary = new Salary(null, mois, salaireBrut, salaireNet, primePerformance, primeHeuresSupp, deductions, cotisationsSociales, avanceApresReduction, employee);

        // Enregistrer le salaire dans la base de données
        return salaryRepository.save(salary);
    }

    private Long calculateDeductions(Employee employee, LocalDate mois) {
        // Récupérer toutes les absences dans le mois pour l'employé
        LocalDate debutMois = mois.withDayOfMonth(1);
        LocalDate finMois = mois.withDayOfMonth(mois.lengthOfMonth());
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


    public String generatePayslipWithDetails(Long employeeId, LocalDate mois) {
        StringBuilder paySlip = new StringBuilder();

        // Récupérer l'employé par ID
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));

        // Informations de base
        paySlip.append("Fiche de Paie de ").append(employee.getNom()).append(" ").append(employee.getPrenom()).append("\n");
        paySlip.append("Mois : ").append(mois.getMonth()).append(" ").append(mois.getYear()).append("\n");
        paySlip.append("Poste : ").append(employee.getPoste()).append("\n");
        paySlip.append("Email : ").append(employee.getEmail()).append("\n");
        paySlip.append("Téléphone : ").append(employee.getTelephone()).append("\n");
        paySlip.append("Numéro de Compte Bancaire : ").append("1234567890").append("\n\n");

        // Récupérer les absences pour ce mois
        List<Absence> absences = absenceRepository.findByEmployeeIdAndDateDebutBetween(employeeId, mois.withDayOfMonth(1), mois.withDayOfMonth(mois.lengthOfMonth()));

        // Calculs
        Long salaireBrut = employee.getSalaire(); // Exemple, ajustez selon votre modèle
        Long primePerformance = calculatePrimePerformance(employeeId, mois);
        Long primeHeuresSupp = calculatePrimeHeuresSupp(employee);
        Long deductions = calculateDeductions(employee, mois);
        Long cotisationsSociales = Math.round(salaireBrut * 0.15); // 15% de cotisations sociales
        Long avanceSalaire = employee.getAvanceSalaire();  // Valeur initiale de l'avance

// Calcul de l'augmentation (par exemple 10%)
        Long reductionAvance = Math.round(avanceSalaire * 0.10); // 10% d'augmentation
        Long avanceApresReduction = avanceSalaire + reductionAvance;
        Long salaireNet = Math.round(((salaireBrut - cotisationsSociales - deductions + primePerformance + primeHeuresSupp) - avanceApresReduction) * 0.75); // 25% d'impôts

        // Détails du salaire
        paySlip.append("Salaire de Base : ").append(salaireBrut).append(" TND\n");
        paySlip.append("Prime de Performance : ").append(primePerformance).append(" TND\n");
        paySlip.append("Prime des Heures Supplémentaires : ").append(primeHeuresSupp).append(" TND\n");
        paySlip.append("Cotisations Sociales : ").append(cotisationsSociales).append(" TND\n");
        paySlip.append("Deductions (Absences et Retards) : ").append(deductions).append(" TND\n");
        paySlip.append("Avance sur Salaire après réduction : ").append(avanceApresReduction).append(" TND\n");
        paySlip.append("Salaire Net : ").append(salaireNet).append(" TND\n");

        // Détails des absences
        paySlip.append("\nDétails des Absences :\n");
        for (Absence absence : absences) {
            paySlip.append("Type : ").append(absence.getType()).append(" - Du ").append(absence.getDateDebut())
                    .append(" au ").append(absence.getDateFin()).append("\n");
        }

        // Détails des performances
        paySlip.append("\nDétails des Performances :\n");
        List<Performance> performances = performanceRepository.findByEmployeeIdAndDateEvaluationBetween(employeeId, mois.withDayOfMonth(1), mois.withDayOfMonth(mois.lengthOfMonth()));
        for (Performance performance : performances) {
            paySlip.append("Date d'évaluation : ").append(performance.getDateEvaluation())
                    .append(" - Note : ").append(performance.getNote()).append("\n");
        }

        // Retourner la fiche de paie générée
        return paySlip.toString();
    }
}

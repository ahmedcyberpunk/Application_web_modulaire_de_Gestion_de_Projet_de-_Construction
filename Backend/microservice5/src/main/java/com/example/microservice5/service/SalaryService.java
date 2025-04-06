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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        long deductionParJour = employee.getSalaire() / 26;
        long salaireParHeure = deductionParJour / 8;

        for (Absence absence : absences) {
            long joursAbsence = absence.getDateFin().toEpochDay() - absence.getDateDebut().toEpochDay() + 1;
            switch (absence.getType()) {
                case SANS_SOLDE:
                    deduction += joursAbsence * deductionParJour;
                    break;
                case MALADIE:
                    if (joursAbsence <= 3) {
                        // Les 3 premiers jours pris en charge par CNAM
                        deduction += 0;
                    } else if (joursAbsence <= 15) {
                        // 50% de déduction du 4e au 15e jour
                        deduction += (joursAbsence - 3) * (deductionParJour / 2);
                    } else {
                        // 50% du 4e au 15e et 75% au-delà
                        deduction += (12 * (deductionParJour / 2))
                                + ((joursAbsence - 15) * ((deductionParJour * 3) / 4));
                    }
                    break;

                case RETARD:
                    // Déduction au prorata des heures manquées
                    if (absence.getDureeHeures() != null && absence.getDureeHeures() > 0) {
                        deduction += absence.getDureeHeures() * salaireParHeure;
                    }
                    break;
                case CONGE_PAYE:
                    // Pas de déduction
                    break;
                default:
                    // Si jamais d'autres types sont ajoutés
                    break;
            }
        }
        return deduction;
    }

    // Fonction pour calculer la prime de performance
    private Long calculatePrimePerformance(Long employeeId, LocalDate mois) {
        List<Performance> performances = performanceRepository.findByEmployeeIdAndDateEvaluationBetween(
                employeeId, mois.withDayOfMonth(1), mois.withDayOfMonth(mois.lengthOfMonth()));

        Employee employee = findEmployeeById(employeeId);
        long salaireDeBase = employee.getSalaire();  // Salaire de base de l'employé
        long prime = 0L;

        for (Performance perf : performances) {
            if (perf.getNote() >= 9) {
                prime+= Math.round(salaireDeBase * 0.20); // 20% du salaire
            } else if (perf.getNote() >= 8) {
                prime += Math.round(salaireDeBase * 0.15); // 15% du salaire
            } else if (perf.getNote() >= 6) {
                prime += Math.round(salaireDeBase * 0.10); // 10% du salaire
            } else if (perf.getNote() >= 5) {
                prime += Math.round(salaireDeBase * 0.05); // 5% du salaire
            }
            // Pas de prime pour les notes inférieures à 5
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
        List<Salary> allSalaries = salaryRepository.findByEmployeeIdOrderByMoisDesc(employeeId);
        List<Salary> uniqueSalaries = new ArrayList<>();

        Set<String> salarySignatures = new HashSet<>();

        for (Salary salaire : allSalaries) {

            String signature = salaire.getMois() + "|" +
                    salaire.getSalaireBrut() + "|" +
                    salaire.getAvanceSalaire() + "|" +
                    salaire.getSalaireImposable() + "|" +
                    salaire.getSalaireNet() + "|" +
                    salaire.getCotisationCNSS() + "|" +
                    salaire.getCotisationRetraite() + "|" +
                    salaire.getDeductions() + "|" +
                    salaire.getPrimePerformance() + "|" +
                    salaire.getPrimeHeuresSupp() + "|" +
                    salaire.getImpotRevenu() ;


            if (!salarySignatures.contains(signature)) {
                uniqueSalaries.add(salaire);
                salarySignatures.add(signature);
            }
        }

        return uniqueSalaries;
    }


    public List<Salary> getSalariesBetweenDates(Long employeeId, LocalDate startDate, LocalDate endDate) {
        List<Salary> allSalaries = salaryRepository.findByEmployeeIdAndMoisBetween(employeeId, startDate, endDate);
        List<Salary> uniqueSalaries = new ArrayList<>();

        Set<String> salarySignatures = new HashSet<>();

        for (Salary salaire : allSalaries) {

            String signature = salaire.getMois() + "|" +
                    salaire.getSalaireBrut() + "|" +
                    salaire.getAvanceSalaire() + "|" +
                    salaire.getSalaireImposable() + "|" +
                    salaire.getSalaireNet() + "|" +
                    salaire.getCotisationCNSS() + "|" +
                    salaire.getCotisationRetraite() + "|" +
                    salaire.getDeductions() + "|" +
                    salaire.getPrimePerformance() + "|" +
                    salaire.getPrimeHeuresSupp() + "|" +
                    salaire.getImpotRevenu() ;


            if (!salarySignatures.contains(signature)) {
                uniqueSalaries.add(salaire);
                salarySignatures.add(signature);
            }
        }

        return uniqueSalaries;
    }



}

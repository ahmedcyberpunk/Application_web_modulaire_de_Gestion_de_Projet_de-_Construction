package com.example.microservice5.service;


import com.example.microservice5.entity.Employee;
import com.example.microservice5.repository.AbsenceRepository;
import com.example.microservice5.repository.EmployeeRepository;
import com.example.microservice5.repository.PerformanceRepository;
import com.example.microservice5.repository.SalaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService  implements IEmployeeService{
    SalaryRepository salaryRepository;
    AbsenceRepository absenceRepository;
    EmployeeRepository employeeRepository;
    PerformanceRepository performanceRepository;

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id).get();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();

    }
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
    if (employeeRepository.existsById(id)){
    employeeRepository.deleteById(id);
    }else {
    throw new RuntimeException("Employee not found");
}
    }

    public Employee updateEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
          return   employeeRepository.save(employee);
        }else {
       throw new RuntimeException("Employee not found");
        }
    }

    // Trier les employés par nom
    public List<Employee> getEmployeesSortedByName() {
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "nom"));
    }

    // Trier les employés par salaire
    public List<Employee> getEmployeesSortedBySalary() {
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "salaire"));
    }
    //search
    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(keyword, keyword);
    }

    // Méthode pour ajouter des heures supplémentaires à un employé
    public void ajouterHeuresSupp(Long employeeId, Long heuresSupp) {
        // Récupérer l'employé par son ID
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        // Ajouter les heures supplémentaires
        employee.setHeuresSupp(employee.getHeuresSupp() + heuresSupp);

        // Sauvegarder l'employé avec ses nouvelles heures supplémentaires
        employeeRepository.save(employee);
    }

    public void demanderAvanceSalaire(Long employeeId, Long montant) {
        // Récupérer l'employé dans la base de données
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        // Vérifier si le montant de l'avance est valide (doit être un montant positif)
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant de l'avance doit être positif");
        }

        // Calculer 1/3 du salaire brut de l'employé
        Long limiteAvance = employee.getSalaire() / 3;

        // Vérifier si le montant de l'avance demandé ne dépasse pas 1/3 du salaire brut
        if (montant > limiteAvance) {
            throw new IllegalArgumentException("L'avance sur salaire ne peut pas dépasser 1/3 du salaire brut de l'employé");
        }

        Long avanceActuelle = employee.getAvanceSalaire();


        if (avanceActuelle + montant > limiteAvance) {
            throw new IllegalArgumentException("L'avance totale (actuelle + nouvelle) ne peut pas dépasser 1/3 du salaire brut de l'employé");
        }

        // Mettre à jour l'avance sur salaire de l'employé
        employee.setAvanceSalaire(avanceActuelle + montant);

        // Sauvegarder les modifications dans la base de données
        employeeRepository.save(employee);
    }

}

package com.example.microservice5.repository;

import com.example.microservice5.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {





    List<Employee> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);

    @Modifying
    @Query("UPDATE Employee e SET e.heuresSupp = 0, e.avanceSalaire = 0")
    void resetHeuresSuppEtAvance();
}

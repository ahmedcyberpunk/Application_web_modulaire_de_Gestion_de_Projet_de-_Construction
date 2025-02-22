package com.example.microservice5.repository;

import com.example.microservice5.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNomContainingIgnoreCase(String name);
    List<Employee> findByPrenomContainingIgnoreCase(String prenom);
    List<Employee> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
}

package tn.esprit.pigc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pigc.Entity.Employee;
@Repository

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


}
package com.example.microservice5.repository;

import com.example.microservice5.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByEmployeeId(Long employeeId);
    List<Absence> findByEmployeeIdAndDateDebutBetween(Long employeeId, LocalDate start, LocalDate end);
    @Query("SELECT a.id, a.dateDebut, a.dateFin, a.type, e.id, e.nom, e.prenom " +
            "FROM Absence a JOIN a.employee e")
    List<Object[]> findAllAbsencesWithEmployeeInfo();

}

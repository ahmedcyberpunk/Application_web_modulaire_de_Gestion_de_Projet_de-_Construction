package com.example.microservice3.repository;

import com.example.microservice3.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FactureRepository extends JpaRepository<Facture,Long> {
    @Query("SELECT NEW map(FUNCTION('DATE_FORMAT', f.dateEmission, '%Y-%m') AS mois, SUM(f.montantTotal) AS montantTotal) " +
            "FROM Facture f GROUP BY mois ORDER BY mois DESC")
    List<Map<String, Object>> findMontantParMois();
}

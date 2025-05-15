package com.example.microservice1.repository;

import com.example.microservice1.entity.Projet;
import com.example.microservice1.entity.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RapportRepo extends JpaRepository<Rapport, Integer> {
    boolean existsByProjet(Projet projet);

}

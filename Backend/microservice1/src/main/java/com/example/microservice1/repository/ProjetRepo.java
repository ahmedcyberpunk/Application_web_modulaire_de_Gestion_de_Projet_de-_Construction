package com.example.microservice1.repository;

import com.example.microservice1.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetRepo extends JpaRepository<Projet,Integer> {
    void deleteById(Integer id);
    Projet findByIdProjet(Integer idProjet);
    Projet getProjetByIdProjet(Integer idProjet);
}

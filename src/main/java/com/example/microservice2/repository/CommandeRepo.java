package com.example.microservice2.repository;

import com.example.microservice2.entity.Commande;
import com.example.microservice2.entity.EtatCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepo  extends JpaRepository<Commande,Long> {
    List<Commande> findByEtatCommande(EtatCommande etatCommande);

}

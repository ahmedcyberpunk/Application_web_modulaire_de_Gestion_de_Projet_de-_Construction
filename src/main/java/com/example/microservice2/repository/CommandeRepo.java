package com.example.microservice2.repository;

import com.example.microservice2.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepo  extends JpaRepository<Commande,Long> {


}

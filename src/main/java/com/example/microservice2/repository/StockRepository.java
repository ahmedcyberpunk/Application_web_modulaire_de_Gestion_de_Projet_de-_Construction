package com.example.microservice2.repository;

import com.example.microservice2.entity.Ressource;
import com.example.microservice2.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByRessource(Ressource ressource);

    Stock findByRessourceId(Long ressourceId);
}

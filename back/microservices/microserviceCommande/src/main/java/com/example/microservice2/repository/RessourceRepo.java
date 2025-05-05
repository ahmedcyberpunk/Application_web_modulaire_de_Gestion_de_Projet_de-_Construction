package com.example.microservice2.repository;

import com.example.microservice2.entity.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RessourceRepo  extends JpaRepository<Ressource,Long> {
}

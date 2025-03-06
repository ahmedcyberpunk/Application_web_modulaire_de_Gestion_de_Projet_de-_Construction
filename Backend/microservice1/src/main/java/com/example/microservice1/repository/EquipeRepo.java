package com.example.microservice1.repository;

import com.example.microservice1.entity.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipeRepo extends JpaRepository<Equipe, Integer> {
}

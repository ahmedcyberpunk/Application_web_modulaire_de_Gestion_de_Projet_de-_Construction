package com.example.microservice2.repository;

import com.example.microservice2.entity.Fournisseurs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseursRepo  extends JpaRepository<Fournisseurs,Long> {
}

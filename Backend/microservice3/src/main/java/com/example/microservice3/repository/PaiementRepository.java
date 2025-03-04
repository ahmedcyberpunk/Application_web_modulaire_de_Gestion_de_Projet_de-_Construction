package com.example.microservice3.repository;

import com.example.microservice3.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Integer> {
   // List<Paiement> findByFactureId(Long factureId);
}

package com.example.microservice2.repository;

import com.example.microservice2.entity.CartRessource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRessourceRepository extends JpaRepository<CartRessource, Long> {
    public void deleteById (Long id);
}

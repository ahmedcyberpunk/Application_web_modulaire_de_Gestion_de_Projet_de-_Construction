package com.example.microservice1.repository;

import com.example.microservice1.entity.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RessourceRepo extends JpaRepository<Ressource, Integer> {

}

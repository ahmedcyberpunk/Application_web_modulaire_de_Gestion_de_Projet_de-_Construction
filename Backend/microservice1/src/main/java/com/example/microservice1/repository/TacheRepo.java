package com.example.microservice1.repository;
import com.example.microservice1.entity.Projet;

import com.example.microservice1.entity.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TacheRepo extends JpaRepository<Tache, Integer> {




}

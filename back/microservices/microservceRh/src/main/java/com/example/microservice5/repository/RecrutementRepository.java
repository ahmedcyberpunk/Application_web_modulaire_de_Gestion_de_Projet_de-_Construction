package com.example.microservice5.repository;


import com.example.microservice5.entity.Recrutement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecrutementRepository extends JpaRepository<Recrutement, Long> {
}

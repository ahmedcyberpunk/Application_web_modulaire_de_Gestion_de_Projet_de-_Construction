package com.example.microservice1.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TacheDTO {
    private Integer idTache;
    private String nomTache;
    private String descriptionTache;
    private LocalDate dateDebutTache;
    private LocalDate dateFinTache;
    private int progresTache;
    private Projet.Statut statut;
}

package com.example.microservice1.entity;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjetDTO {
    private Integer idProjet;
    private String nomProjet;
    private String descriptionProjet;
    private LocalDate dateDebutProjet;
    private LocalDate dateFinProjet;
    private int budgetProjet;
    private Projet.Statut statut;
    private List<TacheDTO> taches;
}

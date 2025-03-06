package com.example.microservice1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProjet;
    private String nomProjet;
    private String descriptionProjet;
    private LocalDate dateDebutProjet;
    private LocalDate dateFinProjet;
    private int budgetProjet;
    @Enumerated(EnumType.STRING)
    private Statut statut;

    @OneToOne
    @JsonIgnore
    private Equipe equipe;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="projet")
    @JsonIgnore
    private List<Tache> Taches;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="projet")
    @JsonIgnore
    private List<Rapport> Rapports;





    public enum Statut{
        INPROGRESS,COMPLETED,PENDING
    }


}

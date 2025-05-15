package com.example.microservice1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTache;
    private String nomTache;
    private String descriptionTache;
    private LocalDate dateDebutTache;
    private LocalDate dateFinTache;
    private int progresTache;
    @Enumerated(EnumType.STRING)
    private Projet.Statut statut;
    @OneToOne
    @JsonIgnore
    private User Responsable;


    @ManyToOne
    @JsonIgnore
    Projet projet;

    @OneToOne
    @JsonIgnore
    private User user;



}

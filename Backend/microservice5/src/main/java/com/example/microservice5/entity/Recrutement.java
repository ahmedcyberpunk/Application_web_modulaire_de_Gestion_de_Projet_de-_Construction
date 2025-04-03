package com.example.microservice5.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recrutement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String commentaire;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateNaissance;
    String email;
    String telephone;
    String poste;
    boolean traiter;

    @Lob
    private byte[] fichier;

    @ManyToOne
    @JsonIgnore
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private TypeDemande typeDemande;


    public enum TypeDemande {
        RECRUTEMENT,
        STAGE,
        AUTRE
    }



}

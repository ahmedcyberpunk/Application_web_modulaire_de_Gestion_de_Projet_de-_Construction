package com.example.microservice5.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recrutement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identifiant unique

    private String nom;  // Nom du candidat
    private String prenom;  // Prénom du candidat
    private String commentaire;  // Commentaire sur la demande

    @Lob
    private byte[] fichier;  // Le fichier (par exemple, un CV ou lettre de motivation)

    @ManyToOne
    @JsonIgnore
    private Employee employee;  // Lien avec l'entité Employee (si nécessaire)

    @Enumerated(EnumType.STRING)
    private TypeDemande typeDemande;

    // Enum pour les types de demande
    public enum TypeDemande {
        RECRUTEMENT,
        STAGE,
        AUTRE
    }



}

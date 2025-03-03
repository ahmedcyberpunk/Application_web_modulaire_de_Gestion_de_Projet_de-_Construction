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

public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;

    String nom;
    String prenom;
    String typeDemande;
    String commentaire;

    enum typeDemande{
        conge,
        avacance,
        autre
    }

    @ManyToOne
    @JsonIgnore
    Employee employee;

}

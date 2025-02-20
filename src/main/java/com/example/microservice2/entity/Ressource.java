package com.example.microservice2.entity;

import com.example.microservice2.entity.Fournisseurs;
import com.example.microservice2.entity.Typeproduit;
import jakarta.persistence.*;
import lombok.*;
import com.example.microservice2.entity.Commande;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Ressource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduit;

    private String description;
    private String nomProduit;
    private double prixUnitaire;

    @Enumerated(EnumType.STRING)
    private Typeproduit typeProduit;

    @ManyToOne
    @JoinColumn(name = "id_fournisseur", nullable = false)
    private Fournisseurs fournisseur;


}

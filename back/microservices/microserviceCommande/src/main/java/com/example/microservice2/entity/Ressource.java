package com.example.microservice2.entity;

import com.example.microservice2.entity.Fournisseurs;
import com.example.microservice2.entity.Typeproduit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import com.example.microservice2.entity.Commande;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(nullable = false)
    private boolean active = true;
    @Column(nullable = true)
    @JsonProperty("imageUrl")
    private String imageUrl; // Stocke l'URL de l'image

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageData;


    @ManyToOne
    @JoinColumn(name = "id_fournisseur", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Fournisseurs fournisseur;
    @Column(nullable = false)
    private int quantiteDisponible;

    public Object getId() {
        return idProduit;
    }
}

package com.example.microservice2.entity;

import com.example.microservice2.entity.EtatCommande;
import com.example.microservice2.entity.Ressource;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommande;

    @Temporal(TemporalType.DATE)
    private Date dateCommande;

    @Enumerated(EnumType.STRING)
    private EtatCommande etatCommande;

    @ManyToMany
    @JoinTable(
            name = "commande_ressource",
            joinColumns = @JoinColumn(name = "id_commande"),
            inverseJoinColumns = @JoinColumn(name = "id_produit")
    )
    private List<Ressource> ressources;
}

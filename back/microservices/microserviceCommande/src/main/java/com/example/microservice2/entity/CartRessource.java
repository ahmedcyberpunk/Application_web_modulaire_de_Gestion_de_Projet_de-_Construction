package com.example.microservice2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
public class CartRessource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "ressource_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ressource ressource;

    private int quantite;

    // Calcul du total pour cette ressource (quantité * prix unitaire)
    public double getTotalPrice() {
        return this.ressource.getPrixUnitaire() * this.quantite;
    }

    // Relation avec la commande

}

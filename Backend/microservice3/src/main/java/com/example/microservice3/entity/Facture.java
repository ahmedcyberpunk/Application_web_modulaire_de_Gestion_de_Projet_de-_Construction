package com.example.microservice3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacture;

    private String reference;

    private Integer montantTotal;

    @Temporal(TemporalType.DATE)
    private Date dateEmission;

    @Enumerated(EnumType.STRING)
    private StatutFacture statut;

    private String description;
    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL)
    private List<Echeance> echeances;

    // Une facture peut avoir plusieurs paiements
    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL)
    private List<Paiement> paiements;
}



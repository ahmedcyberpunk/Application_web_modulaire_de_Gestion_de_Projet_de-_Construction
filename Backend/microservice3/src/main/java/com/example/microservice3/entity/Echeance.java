package com.example.microservice3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Echeance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEcheance;

    private Double montantEcheance;
    private String dateEcheance;
    private Double montantInteret;
    private Double montantTotalAPayer;
    private String statut; // 'En attente', 'Payée', 'Retard'
    private Double tauxInteret; // Taux d'intérêt en %

    @ManyToOne
    @JoinColumn(name = "facture_id")
    Facture facture;

    @ManyToOne
    @JoinColumn(name = "mode_paiement_id")
    ModePaiement modePaiement;
}

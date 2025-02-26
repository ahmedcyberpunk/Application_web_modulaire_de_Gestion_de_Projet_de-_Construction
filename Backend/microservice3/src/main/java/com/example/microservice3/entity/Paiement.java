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
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaiement;

    private BigDecimal montantPaye;

    @Temporal(TemporalType.DATE)
    private Date datePaiement;

    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;

    // Plusieurs paiements peuvent être liés à une seule facture
    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;

    // Un paiement peut être lié à une échéance spécifique
    @OneToOne
    @JoinColumn(name = "echeance_id")
    private Echeance echeance;
}


package com.example.microservice3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ModePaiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idModePaiement;

    private String mode; // Virement bancaire, Chèque, Espèces, etc.

    @OneToMany(mappedBy = "modePaiement")
    private List<Echeance> echeances;
}

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
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    double montant_total;
    double montant_paye;
    @Enumerated(EnumType.STRING)
    Type type;

}

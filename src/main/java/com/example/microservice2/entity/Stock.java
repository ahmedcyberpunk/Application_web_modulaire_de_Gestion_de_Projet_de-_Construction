package com.example.microservice2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStock;

    @OneToOne
    @JoinColumn(name = "ressource_id", nullable = false)
    private Ressource ressource;

    @Column(nullable = false)
    private int quantiteDisponible;
}

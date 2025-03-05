package com.example.microservice2.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

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

    // Champ pour stocker les ressources sous forme de chaîne JSON
    @Lob
    private String ressourcesJson;

    // Méthode pour récupérer les ressources depuis la chaîne JSON


}

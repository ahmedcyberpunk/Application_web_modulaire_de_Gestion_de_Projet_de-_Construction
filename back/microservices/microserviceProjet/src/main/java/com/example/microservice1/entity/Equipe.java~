package com.example.microservice1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEquipe;
    private String chefEquipe;

    @OneToOne(mappedBy="equipe")
    @JsonIgnore
    private Projet projet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="equipe")
    @JsonIgnore
    private List<User> Users;



}

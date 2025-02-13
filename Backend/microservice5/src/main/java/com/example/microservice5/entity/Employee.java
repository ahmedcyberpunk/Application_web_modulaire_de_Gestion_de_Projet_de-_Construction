package com.example.microservice5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nom;
    String prenom;
    LocalDate dateNaissance;
    String email;
    String telephone;
    String poste;
    Double salaire;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    List<Absence> absences;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    List<Performance> performances;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    List<Salary> salaries;

}

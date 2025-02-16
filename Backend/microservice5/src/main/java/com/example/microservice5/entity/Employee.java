package com.example.microservice5.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateNaissance;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateEmbauche;
    String email;
    String telephone;
    String poste;
    Long salaire;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    List<Absence> absences;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Performance> performances;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)

    List<Salary> salaries;

}

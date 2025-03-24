package com.example.microservice5.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
     LocalDate dateDebut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
     LocalDate dateFin;
    // Durée en heures pour les retards
    Double dureeHeures;
    @Enumerated(EnumType.STRING)
     AbsenceType type;

    @ManyToOne
    @JsonIgnore
    Employee employee;
}

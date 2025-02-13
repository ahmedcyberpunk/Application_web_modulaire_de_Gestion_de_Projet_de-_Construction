package com.example.microservice5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
     LocalDate dateDebut;
     LocalDate dateFin;

    @Enumerated(EnumType.STRING)
     AbsenceType type;

    @ManyToOne
    Employee employee;
}

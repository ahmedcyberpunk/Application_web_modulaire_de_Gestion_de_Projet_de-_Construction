package com.example.microservice5.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;
     int note;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     LocalDate dateEvaluation;
     String commentaire;

    @ManyToOne
    @JsonIgnore
    Employee employee;
}

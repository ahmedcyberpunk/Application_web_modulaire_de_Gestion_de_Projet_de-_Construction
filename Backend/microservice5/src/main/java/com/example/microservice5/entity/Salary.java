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
        public class Salary {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            Long id;
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate mois;
            Long salaireBrut;
            Long salaireNet;

           Long salaireImposable;
            Long primePerformance;  // Prime basée sur la performance
            Long primeHeuresSupp;   // Prime basée sur les heures supplémentaires

            Long cotisationCNSS;      // CNSS (9,18% pour le salarié)
            Long cotisationRetraite;  // Retraite complémentaire (ex: 4%)


            Long impotRevenu;         // IRPP (Impôt sur le revenu progressif)
            Long avanceSalaire;


            Long deductions;


            @ManyToOne
            @JsonIgnore
            Employee employee;
        }

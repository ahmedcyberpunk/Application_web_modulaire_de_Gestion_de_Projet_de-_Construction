package tn.esprit.pi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Papier_autorisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id_Autorisation;
    String Type_Autorisation;
    String Statut;
    Date Date_Obtention;
@ManyToOne
   Terrain terrain;






















}

package tn.esprit.pi.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    String type_Autorisation;
    String statut;
    String  description;
    String imagePath;

int notif;
    @ManyToOne
    @JsonIgnore
   Terrain terrain;






















}

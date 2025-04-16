package tn.esprit.pi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Terrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int idTerrain;
     String nom;
     String localisation;
     double superficie;
     String statutJuridique;
    @Enumerated(EnumType.STRING)
    Typesol typeSol;
    String imagePath;
    int notif;
    int ban;
    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "terrain")
    @JsonIgnore
    List<Papier_autorisation> papierAutorisationConstructions;

@OneToMany(cascade = CascadeType.ALL,mappedBy = "terrain")
@JsonIgnore

    List<Contrat_Terrain> contartTerrains;


}

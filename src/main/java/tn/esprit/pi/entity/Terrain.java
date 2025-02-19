package tn.esprit.pi.entity;

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

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "terrain")
    List<Papier_autorisation> papierAutorisationConstructions;


@OneToMany(cascade = CascadeType.ALL,mappedBy = "terrain")

    List<Contart_Terrain> contartTerrains;


}

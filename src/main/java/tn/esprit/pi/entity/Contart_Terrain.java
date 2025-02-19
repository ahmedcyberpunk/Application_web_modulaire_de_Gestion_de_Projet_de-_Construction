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
public class Contart_Terrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id_Contrat;
    int id_Proprietaire;
    Date date_signature;
    String type_contart;
    boolean statut_Contrat;
    @ManyToOne
    Terrain terrain;








}

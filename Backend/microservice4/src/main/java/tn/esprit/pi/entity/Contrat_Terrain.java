package tn.esprit.pi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contrat_Terrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_Contrat;
String nom_admin;

String nomProprietaire;
String prenom_Proprietaire;
    Date date_signature;
    String type_contrat;
    String statut_Contrat;
    String signatureImage;
    String signatureClient;
    String imageterre;
    int telephone;
    int indice;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idTerrain", nullable = false)
@JsonIgnore

    Terrain terrain;








}

package tn.esprit.pi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entity.Contrat_Terrain;

import java.util.List;

public interface ContratRepo extends JpaRepository<Contrat_Terrain, Integer> {

    Contrat_Terrain findByTerrainIdTerrain(Long idTerrain);
    List<Contrat_Terrain> findByNomProprietaire(String nom_Proprietaire);

}

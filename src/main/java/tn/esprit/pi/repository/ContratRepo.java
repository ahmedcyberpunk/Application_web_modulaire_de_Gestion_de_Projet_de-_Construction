package tn.esprit.pi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entity.Contrat_Terrain;

public interface ContratRepo extends JpaRepository<Contrat_Terrain, Integer> {

    Contrat_Terrain findByTerrainIdTerrain(Long idTerrain);


}

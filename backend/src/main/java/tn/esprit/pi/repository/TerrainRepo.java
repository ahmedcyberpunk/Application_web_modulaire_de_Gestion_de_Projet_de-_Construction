package tn.esprit.pi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entity.Terrain;

import java.util.Optional;

public interface TerrainRepo extends JpaRepository<Terrain,Integer> {


    Terrain findByIdTerrain(Long id);


}

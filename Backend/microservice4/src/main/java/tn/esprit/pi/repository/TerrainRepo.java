package tn.esprit.pi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pi.entity.Terrain;

import java.util.List;
import java.util.Optional;

public interface TerrainRepo extends JpaRepository<Terrain,Integer> {

    Terrain findByLocalisation(String location);


    Terrain findByIdTerrain(Long id);
    @Query("SELECT t FROM Terrain t WHERE t.localisation IN :locations ORDER BY FIELD(t.localisation, :locations)")
    List<Terrain> findByLocalisationInOrderByField(@Param("locations") List<String> locations);

    List<Terrain> findByLocalisationNotIn(List<String> localisations);
}

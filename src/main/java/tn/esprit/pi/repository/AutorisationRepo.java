package tn.esprit.pi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entity.Papier_autorisation;

public interface AutorisationRepo extends JpaRepository<Papier_autorisation, Integer> {



}

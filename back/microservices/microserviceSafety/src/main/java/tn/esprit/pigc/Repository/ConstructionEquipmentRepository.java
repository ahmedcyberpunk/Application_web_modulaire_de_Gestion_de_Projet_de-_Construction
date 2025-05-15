package tn.esprit.pigc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pigc.Entity.ConstructionEquipment;

@Repository

public interface ConstructionEquipmentRepository extends JpaRepository<ConstructionEquipment, Integer> {
}

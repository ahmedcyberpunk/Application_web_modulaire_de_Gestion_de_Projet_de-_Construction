package tn.esprit.pigc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.pigc.Entity.EquipmentInspection;

import java.util.List;

@Repository

public interface EquipmentInspectionRepository extends JpaRepository<EquipmentInspection, Integer> {
//    @Query("SELECT e FROM EquipmentInspection e JOIN FETCH e.equipment JOIN FETCH e.ppe")
//    List<EquipmentInspection> findAllWithEquipmentAndPPE();
}


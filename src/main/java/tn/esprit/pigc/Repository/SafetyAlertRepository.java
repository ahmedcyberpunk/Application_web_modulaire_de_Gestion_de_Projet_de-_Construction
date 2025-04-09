package tn.esprit.pigc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pigc.Entity.AlertType;
import tn.esprit.pigc.Entity.SafetyAlert;

import java.util.List;

@Repository
public interface SafetyAlertRepository extends JpaRepository<SafetyAlert, Integer> {


    // Get all unresolved alerts
    List<SafetyAlert> findByResolvedFalse();

    // Get alerts by type
    List<SafetyAlert> findByAlertType(AlertType alertType);





}

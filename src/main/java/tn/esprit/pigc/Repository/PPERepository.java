package tn.esprit.pigc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pigc.Entity.PPE;
@Repository
public interface PPERepository extends JpaRepository<PPE,Integer> {


}

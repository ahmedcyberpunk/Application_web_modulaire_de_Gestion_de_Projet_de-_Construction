package tn.esprit.pi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi.entity.SearchHistory;

import java.util.List;
import java.util.Optional;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    // Find the search history of a specific user
    List<SearchHistory> findByUsernameOrderBySearchCountDesc(String username);

    // Find a specific location search history for a user
    Optional<SearchHistory> findByUsernameAndLocation(String username, String location);
}

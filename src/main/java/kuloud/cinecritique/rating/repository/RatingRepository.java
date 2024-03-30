package kuloud.cinecritique.rating.repository;

import kuloud.cinecritique.rating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}

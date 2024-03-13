package kuloud.cinecritique.movie.repository;


import kuloud.cinecritique.movie.entity.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transacional;
import java.util.Optional;

@Transactional
public interface GenresRepository extends JpaRepository<Genres, Long> {
    // 이름으로 장르 조회
    Optional<Genres> findByName(String name);
}
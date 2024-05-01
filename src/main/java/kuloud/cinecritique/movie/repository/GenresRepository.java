package kuloud.cinecritique.movie.repository;


import jakarta.transaction.Transactional;
import kuloud.cinecritique.movie.entity.Genre;
import kuloud.cinecritique.movie.entity.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
/*import javax.transaction.Transacional;*/
import java.util.Optional;

@Transactional
public interface GenresRepository extends JpaRepository<Genre, Long> {
    // 이름으로 장르 조회
    Optional<Genre> findByName(String name);
}
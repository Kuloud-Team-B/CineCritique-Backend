package kuloud.cinecritique.movie.service;

import kuloud.cinecritique.movie.entity.Genres;
import kuloud.cinecritique.movie.repository.GenresRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenresRepo genresRepo;

    // 장르 이름을 받아서, 해당하는 장르가 있는지 조회하고 없으면 새로 생성하여 반환
    @Transactional
    public Genres findOrCreateNew(String name) {
        return genresRepo.findByName(name)
                .orElseGet(() -> genresRepo.save(new Genres(name)));
    }
}
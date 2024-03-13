package kuloud.cinecritique.movie.controller;

import kuloud.cinecritique.movie.dto.MovieDto;
import kuloud.cinecritique.movie.dto.RecommendationDto;
import kuloud.cinecritique.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movies")
    public ResponseEntity<?> retrieveMovies() {
        List<MovieDto> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    // 영화 추천 기능 => 페이지네이션 일부 수정 예정
    @PostMapping("/recommendation")
    public ResponseEntity<List<MovieDto>> getRecommendation(@RequestBody RecommendationDto recommendationDto) {
        List<MovieDto> recommendedMovies = movieService.getRecommendation(recommendationDto);
        return ResponseEntity.ok(recommendedMovies);
    }
}

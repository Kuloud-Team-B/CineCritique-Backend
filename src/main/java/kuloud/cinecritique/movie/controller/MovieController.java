package kuloud.cinecritique.movie.controller;

import kuloud.cinecritique.movie.dto.MovieDto;
import kuloud.cinecritique.movie.dto.MoviePostDto;
import kuloud.cinecritique.movie.entity.MovieGenre;
import kuloud.cinecritique.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<MovieDto> getMovie(@RequestParam String name) {
        return ResponseEntity.ok(movieService.getMovieWithName(name));
    }

    @GetMapping("/list")
    public ResponseEntity<List<MovieDto>> getMovieWithGenre(@RequestParam MovieGenre genre) {
        return ResponseEntity.ok(movieService.getMovieWithGenre(genre));
    }

    @PostMapping
    public ResponseEntity<Void> postMovie(@RequestBody MoviePostDto moviePostDto) {
        return movieService.saveMovie(moviePostDto);
    }
}

package kuloud.cinecritique.movie.controller;

import kuloud.cinecritique.movie.dto.MovieDto;
import kuloud.cinecritique.movie.dto.MoviePostDto;
import kuloud.cinecritique.movie.dto.MovieUpdateDto;
import kuloud.cinecritique.movie.entity.MovieGenre;
import kuloud.cinecritique.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieApiController {
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<MovieDto> getMovie(@RequestParam String name) {
        MovieDto result = movieService.getMovieWithName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDto>> getMovieWithGenre(@RequestParam MovieGenre genre) {
        List<MovieDto> result = movieService.getMovieWithGenre(genre);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMovie() {
        List<MovieDto> result = movieService.getAllMovie();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> postMovie(@RequestBody MoviePostDto moviePostDto) {
        movieService.saveMovie(moviePostDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping
    public ResponseEntity<Void> updateMovie(@RequestBody MovieUpdateDto movieUpdateDto) {
        movieService.updateMovie(movieUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteMovie(@RequestParam String name) {
        movieService.deleteMovie(name);
        return ResponseEntity.noContent().build();
    }
}

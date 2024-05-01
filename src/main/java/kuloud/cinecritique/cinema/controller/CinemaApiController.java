package kuloud.cinecritique.cinema.controller;

import kuloud.cinecritique.cinema.dto.CinemaDto;
import kuloud.cinecritique.cinema.dto.CinemaPostDto;
import kuloud.cinecritique.cinema.dto.CinemaUpdateDto;
import kuloud.cinecritique.cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema")
public class CinemaApiController {
    private final CinemaService cinemaService;

    @GetMapping
    public ResponseEntity<CinemaDto> getCinema(@RequestParam String name) {
        CinemaDto result = cinemaService.getCinema(name);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> postCinema(@RequestBody CinemaPostDto cinemaPostDto) {
        cinemaService.saveCinema(cinemaPostDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping
    public ResponseEntity<Void> updateCinema(@RequestBody CinemaUpdateDto cinemaUpdateDto) {
        cinemaService.updateCinema(cinemaUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteCinema(@RequestParam String name) {
        cinemaService.deleteCinema(name);
        return ResponseEntity.noContent().build();
    }

}

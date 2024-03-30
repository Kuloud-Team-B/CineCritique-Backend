package kuloud.cinecritique.rating.controller;

import kuloud.cinecritique.rating.dto.RatingRequestDto;
import kuloud.cinecritique.rating.entity.Rating;
import kuloud.cinecritique.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Rating> addRating(@RequestBody RatingRequestDto ratingRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = ((kuloud.cinecritique.member.entity.Member) authentication.getPrincipal()).getId();

        Rating newRating = ratingService.addRating(ratingRequestDto, memberId);
        return ResponseEntity.ok(newRating);
    }
}

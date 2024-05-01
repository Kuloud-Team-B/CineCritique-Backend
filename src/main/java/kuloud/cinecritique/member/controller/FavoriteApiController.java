package kuloud.cinecritique.member.controller;

import kuloud.cinecritique.member.dto.FavoriteDeleteDto;
import kuloud.cinecritique.member.dto.FavoriteDto;
import kuloud.cinecritique.member.dto.FavoritePostDto;
import kuloud.cinecritique.member.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/favorite")
public class FavoriteApiController {
    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Void> createFavorite(FavoritePostDto dto) {
        String userEmail = getEmailFromToken();

        favoriteService.saveFavorite(userEmail, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFavorite(FavoriteDeleteDto dto) {
        String userEmail = getEmailFromToken();

        favoriteService.deleteFavorite(userEmail, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteDto>> getFavoritesFromUser() {
        String userEmail = getEmailFromToken();

        List<FavoriteDto> result = favoriteService.getMyFavorites(userEmail);
        return ResponseEntity.ok(result);
    }

    private String getEmailFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

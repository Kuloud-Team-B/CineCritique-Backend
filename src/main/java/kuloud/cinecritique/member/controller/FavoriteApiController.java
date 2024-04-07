package kuloud.cinecritique.member.controller;

import kuloud.cinecritique.member.dto.FavoriteDeleteDto;
import kuloud.cinecritique.member.dto.FavoriteDto;
import kuloud.cinecritique.member.dto.FavoritePostDto;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/favorite")
public class FavoriteApiController {
    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Void> createFavorite(@RequestBody FavoritePostDto dto, Authentication authentication) {
        favoriteService.saveFavorite(getCurrentMemberId(authentication), dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFavorite(@RequestBody FavoriteDeleteDto dto, Authentication authentication) {
        favoriteService.deleteFavorite(getCurrentMemberId(authentication), dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteDto>> getFavoritesFromUser(Authentication authentication) {
        List<FavoriteDto> result = favoriteService.getMyFavorites(getCurrentMemberId(authentication));
        return ResponseEntity.ok(result);
    }

    private Long getCurrentMemberId(Authentication authentication) {
        return ((Member) authentication.getPrincipal()).getId();
    }
}

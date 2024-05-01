package kuloud.cinecritique.member.controller;

import kuloud.cinecritique.member.dto.LikesDeleteDto;
import kuloud.cinecritique.member.dto.LikesDto;
import kuloud.cinecritique.member.dto.LikesPostDto;
import kuloud.cinecritique.member.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/likes")
public class LikesApiController {
    private final LikesService likesService;

    @PostMapping
    public ResponseEntity<Void> createLikes(LikesPostDto dto) {
        String userEmail = getEmailFromToken();

        likesService.saveLikes(userEmail, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLikes(LikesDeleteDto dto) {
        String userEmail = getEmailFromToken();

        likesService.deleteLikes(userEmail, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LikesDto>> getLikesFromUser() {
        String userEmail = getEmailFromToken();

        List<LikesDto> result = likesService.getMyLikes(userEmail);
        return ResponseEntity.ok(result);
    }

    private String getEmailFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

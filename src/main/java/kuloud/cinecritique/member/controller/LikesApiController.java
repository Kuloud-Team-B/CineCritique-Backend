package kuloud.cinecritique.member.controller;

import kuloud.cinecritique.member.dto.LikesDeleteDto;
import kuloud.cinecritique.member.dto.LikesDto;
import kuloud.cinecritique.member.dto.LikesPostDto;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/likes")
public class LikesApiController {
    private final LikesService likesService;

    @PostMapping
    public ResponseEntity<Void> createLikes(@RequestBody LikesPostDto dto, Authentication authentication) {
        likesService.saveLikes(getCurrentMemberId(authentication), dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLikes(@RequestBody LikesDeleteDto dto, Authentication authentication) {
        likesService.deleteLikes(getCurrentMemberId(authentication), dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LikesDto>> getLikesFromUser(Authentication authentication) {
        List<LikesDto> result = likesService.getMyLikes(getCurrentMemberId(authentication));
        return ResponseEntity.ok(result);
    }

    private Long getCurrentMemberId(Authentication authentication) {
        return ((Member) authentication.getPrincipal()).getId();
    }
}

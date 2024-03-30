package kuloud.cinecritique.post.controller;

import jakarta.validation.Valid;
import kuloud.cinecritique.post.dto.LikeRequestDto; // 가정하에 추가됨
import kuloud.cinecritique.post.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // Lombok을 사용해 생성자 주입
@RequestMapping("/likes") // 보통 RESTful 경로는 복수형을 사용
public class LikeController {

    private final LikeService likeService;

    // @Autowired 없이 Lombok @RequiredArgsConstructor로 생성자 주입

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid LikeRequestDto likeRequestDto) {
        likeService.insert(likeRequestDto);
        return ResponseEntity.ok().build(); // ResponseEntity 사용
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody @Valid LikeRequestDto likeRequestDto) {
        likeService.delete(likeRequestDto);
        return ResponseEntity.ok().build(); // ResponseEntity 사용
    }

    @PostMapping("/{postId}")
    public ResponseEntity<?> toggleLike(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Member 클래스 대신 MemberDetails 사용 가정; 실제 구현에 따라 다름
        Long memberId = ((kuloud.cinecritique.member.security.MemberDetails) authentication.getPrincipal()).getMemberId();

        likeService.toggleLike(postId, memberId); // Member ID를 인자로 전달

        return ResponseEntity.ok().build();
    }
}

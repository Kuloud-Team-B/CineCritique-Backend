package kuloud.cinecritique.Like.controller;

import kuloud.cinecritique.Like.service.LikeService;
import kuloud.cinecritique.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<String> toggleLike(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = ((Member) authentication.getPrincipal()).getId();

        boolean likeStatus = likeService.toggleLike(postId, memberId);
        String message = likeStatus ? "좋아요를 추가했습니다." : "좋아요를 취소했습니다.";

        return ResponseEntity.ok(message);
    }
}

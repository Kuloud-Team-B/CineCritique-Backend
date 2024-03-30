package kuloud.cinecritique.post.service;

import kuloud.cinecritique.post.dto.LikeResponseDto;
import kuloud.cinecritique.post.entity.Like;
import kuloud.cinecritique.post.repository.LikeRepository;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.repository.PostRepository;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, MemberRepository memberRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }
    
    @Transactional
    public void toggleLike(Long postId, Long memberId) {
        Optional<Like> like = likeRepository.findByPostIdAndMemberId(postId, memberId);
        if (like.isPresent()) {
            likeRepository.delete(like.get());
        } else {
            Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found"));
            Like newLike = new Like(post, member);
            likeRepository.save(newLike);
        }
    }
    @PostMapping("/{postId}/like")
    public ResponseEntity<LikeResponseDto> toggleLike(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = ((kuloud.cinecritique.member.entity.Member) authentication.getPrincipal()).getId();

        boolean likeStatus = likeService.toggleLike(postId, memberId);
        String message = likeStatus ? "좋아요를 추가했습니다." : "좋아요를 취소했습니다.";

        LikeResponseDto responseDto = new LikeResponseDto(message, likeStatus);

        return ResponseEntity.ok(responseDto);
    }

}

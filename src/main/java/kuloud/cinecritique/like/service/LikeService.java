package kuloud.cinecritique.like.service;

import kuloud.cinecritique.like.entity.Like;
import kuloud.cinecritique.like.repository.LikeRepository;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.repository.PostRepository;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean toggleLike(Long postId, Long memberId) {
        Optional<Like> like = likeRepository.findByPostIdAndMemberId(postId, memberId);
        if (like.isPresent()) {
            likeRepository.delete(like.get());
        } else {
            Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found"));
            Like newLike = new Like(post, member);
            likeRepository.save(newLike);
        }
        return false;
    }
}

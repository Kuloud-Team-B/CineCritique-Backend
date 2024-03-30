package kuloud.cinecritique.post.repository;

import kuloud.cinecritique.post.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostIdAndMemberId(Long postId, Long MemberId);
    void deleteByPostIdAndMemberId(Long postId, Long MemberId);
}
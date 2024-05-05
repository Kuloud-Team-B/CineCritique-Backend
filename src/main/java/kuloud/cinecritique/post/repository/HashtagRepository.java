package kuloud.cinecritique.post.repository;

import kuloud.cinecritique.post.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    // 사용자 정의 쿼리 메소드
    Optional<Hashtag> findByTagName(String tagName);
    List<Hashtag> findByTagNameContaining(String query);
}

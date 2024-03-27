package kuloud.cinecritique.post.repository;

import kuloud.cinecritique.post.entity.Hashtag;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.entity.PostHashtagMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHashtagMapRepository extends JpaRepository<PostHashtagMap, Long> {
    List<PostHashtagMap> findAllByPost(Post post);

    Optional<PostHashtagMap> findByPostAndHashtag(Post post, Hashtag hashtag);

    void deleteByPostAndHashtag(Post post, Hashtag hashtag);
}

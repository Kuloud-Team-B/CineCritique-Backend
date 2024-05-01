package kuloud.cinecritique.post.repository;

import kuloud.cinecritique.post.entity.Hashtag;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.entity.PostHashtagMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostHashtagMapRepository extends JpaRepository<PostHashtagMap, Long> {
    List<PostHashtagMap> findAllByPost(Post post);

    Optional<PostHashtagMap> findByPostAndHashtag(Post post, Hashtag hashtag);
    Page<PostHashtagMap> findByHashtag(Hashtag hashtag, Pageable pageable);
    Page<PostHashtagMap> findByPost(Post post, Pageable pageable);
    Set<PostHashtagMap> findAllByHashtag(Hashtag hashtag);
}

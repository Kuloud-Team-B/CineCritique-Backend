package kuloud.cinecritique.post.repository;

import kuloud.cinecritique.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
    Post findByTitle(String title);
}

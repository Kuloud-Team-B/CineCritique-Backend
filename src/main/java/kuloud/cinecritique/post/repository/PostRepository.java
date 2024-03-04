package kuloud.cinecritique.post.repository;

import kuloud.cinecritique.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 사용자 정의 쿼리 메소드

    // 특정 해시태그를 가진 게시글을 찾기
    List<Post> findByHashtag(String hashtag);

}

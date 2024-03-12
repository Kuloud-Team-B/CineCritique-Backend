package kuloud.cinecritique.post.repository;

import kuloud.cinecritique.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 사용자 정의 쿼리 메소드
    Optional<Post> findById(Long id);

    // 특정 해시태그를 가진 게시글을 찾기
    List<Post> findByHashtag(String hashtag);


    // 제목 또는 내용에 특정 키워드가 포함된 게시글 검색 (대소문자 구분 없이)
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable);

    // 특정 사용자가 작성한 게시글 목록 조회
    Page<Post> findByMemberId(Long memberId, Pageable pageable);

}

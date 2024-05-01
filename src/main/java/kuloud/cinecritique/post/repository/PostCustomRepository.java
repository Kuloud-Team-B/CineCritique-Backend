package kuloud.cinecritique.post.repository;

import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.post.dto.PostResponseDto;
import kuloud.cinecritique.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCustomRepository{
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByUser(Pageable pageable, Member member);
    Page<Post> findByHashtags(Pageable pageable, String hashtag);

    PageImpl<PostResponseDto> getPostList(String query, Pageable pageable);

    PostResponseDto getPostWithTag(Long id);

    void addLikeCount(Post Post);

    void subLikeCount(Post Post);

    List<PostResponseDto> getBestList();


    // 특정 해시태그를 가진 게시글을 찾기
    List<Post> findByHashtag(String hashtag);


    // 제목 또는 내용에 특정 키워드가 포함된 게시글 검색 (대소문자 구분 없이)
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable);

    // 특정 사용자가 작성한 게시글 목록 조회
    Page<Post> findByMemberId(Long memberId, Pageable pageable);

    Page<Post> findByTitleContaining(String query, Pageable pageable);
}

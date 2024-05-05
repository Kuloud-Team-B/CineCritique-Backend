package kuloud.cinecritique.post.repository;

import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.post.dto.PostResponseDto;
import kuloud.cinecritique.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PostCustomRepository{
    /*
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByUser(Pageable pageable, Member member);
    Page<Post> findByHashtags(Pageable pageable, String hashtag);  // Kept this if it's the preferred naming
     */
    PageImpl<PostResponseDto> getPostList(String query, Pageable pageable);
    PostResponseDto getPostWithTag(Long id);
    void addLikeCount(Post selectedPost);
    void subLikeCount(Post selectedPost);
    List<PostResponseDto> getBestList();
    List<PostResponseDto> getTopList();
    Page<PostResponseDto> getBestPage(int page, int size, Pageable pageable);
    Page<PostResponseDto> getByDateRange(LocalDateTime from, LocalDateTime to, Pageable pageable);
}

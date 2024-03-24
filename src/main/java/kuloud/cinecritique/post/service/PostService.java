package kuloud.cinecritique.post.service;

import kuloud.cinecritique.post.dto.PostRequestDto;
import kuloud.cinecritique.post.entity.PostHashtagMap;
import kuloud.cinecritique.post.repository.HashtagRepository;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.repository.PostRepository;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.member.repository.MemberRepository;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.movie.repository.MovieRepository;
import kuloud.cinecritique.cinema.entity.Cinema;
import kuloud.cinecritique.cinema.repository.CinemaRepository;
import kuloud.cinecritique.goods.entity.Goods;
import kuloud.cinecritique.goods.repository.Goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final HashtagRepository hashtagRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private GoodsRepository goodsRepository;


    @Autowired
    public PostService(PostRepository postRepository, MemberRepository memberRepository, HashtagRepository hashtagRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.hashtagRepository = hashtagRepository;
    }

    // 중복된 Member 조회 로직 제거
    private Member getAuthenticatedMember(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("No member found with nickname: " + nickname));
    }

    // 게시글의 권한 검사 로직 통합
    private void verifyPostOwner(Post post, String username) {
        if (!post.getMember().getNickname().equals(username)) {
            throw new IllegalStateException("You can only edit or delete your own posts.");
        }
    }



    @Transactional
    public Long createPost(PostRequestDto postRequestDto, String nickname) {
        Member member = getAuthenticatedMember(nickname);

        // 해시태그 처리 로직 호출
        // 해시태그 처리 로직 호출하여 PostHashtagMap의 Set 반환받기
        Set<PostHashtagMap> postHashtagMaps = processHashtags(postRequestDto.getHashtag());


        // Movie, Cinema, Goods 외래 키 엔티티 조회
        Movie movie = movieRepository.findById(postRequestDto.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));
        Cinema cinema = cinemaRepository.findById(postRequestDto.getCinemaId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid cinema ID"));
        Goods goods = goodsRepository.findById(postRequestDto.getGoodsId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid goods ID"));

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .member(member)
                .postImg(postRequestDto.getPostImg())
                .rating(postRequestDto.getRating())
                .postHashtagMaps(postHashtagMaps)
                .movie(movie)
                .cinema(cinema)
                .goods(goods)
                .build();

        postRepository.save(post);
        return post.getId();
    }

    private Set<PostHashtagMap> processHashtags(String hashtag) {
        Set<PostHashtagMap> postHashtagMaps = new HashSet<>();
        // 해시태그 문자열을 파싱하여 각 해시태그에 대한 PostHashtagMap 객체를 생성
        // 예시 로직이며, 실제 구현에서는 문자열을 분리하고, 각 해시태그에 대한 Hashtag 엔티티를 조회하거나 생성한 뒤 PostHashtagMap을 생성해야 함
        return postHashtagMaps;
    }

    @Transactional
    public void updatePost(Long postId, PostRequestDto postRequestDto, String nickname) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        verifyPostOwner(post, nickname);

        // 해시태그 처리 로직 호출
        Set<PostHashtagMap> hashtags = processHashtags(postRequestDto.getHashtag());

        post.update(postRequestDto.getTitle(), postRequestDto.getContent(), hashtags);
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId, String nickname) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        verifyPostOwner(post, nickname);

        postRepository.deleteById(postId);
    }
}

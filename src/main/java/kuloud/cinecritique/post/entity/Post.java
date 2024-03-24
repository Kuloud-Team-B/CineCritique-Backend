package kuloud.cinecritique.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.post.entity.Hashtag;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.cinema.entity.Cinema;
import kuloud.cinecritique.goods.entity.Goods;
import lombok.*;
import kuloud.cinecritique.common.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // 추가: 모든 필드를 포함하는 생성자를 자동 생성\
public class Post extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long Id;

    @NotBlank(message = "제목을 입력해주세요.")
    @Column(nullable = false, length = 15)
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Column(nullable = false, length = 200)
    private String content;

    /* 첨부 이미지 처리 필요*/
    @Column(name = "post_img")
    private String postImg;

    // 평점
    @Column(nullable = false)
    private int rating;

    // 해시태그: 해시태그 관계를 PostHashtagMap을 통해 관리
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostHashtagMap> hashtags = new HashSet<>();

    // 조회수
    @ColumnDefault("0")
    @Column(name = "view_count",nullable = false)
    private Integer viewCount;

    // 좋아요 수 관리
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Member.class)
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Movie.class)
    @JoinColumn(name = "movie_id", updatable = false)
    private Movie movie;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Cinema.class)
    @JoinColumn(name = "cinema_id", updatable = false)
    private Cinema cinema;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Goods.class)
    @JoinColumn(name = "goods_id", updatable = false)
    private Goods goods;

    @Builder
    public Post(String title, String content, Member member, String postImg, int rating, Set<PostHashtagMap> postHashtagMaps,
                Movie movie, Cinema cinema, Goods goods) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.postImg = postImg;
        this.rating = rating;
        this.movie = movie;
        this.cinema = cinema;
        this.goods = goods;
    }

    // 게시글 내용 업데이트를 위한 메소드
    public void update(String title, String content, Set<PostHashtagMap> updatedHashtags) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
        if (updatedHashtags != null) {
            this.hashtags.clear();
            this.hashtags.addAll(updatedHashtags);
        }
    }
    // 해시태그 추가
    public void addHashtag(Hashtag hashtag) {
        PostHashtagMap map = new PostHashtagMap(this, hashtag);
        this.hashtags.add(map);
        hashtag.getPosts().add(map);
    }

    // 해시태그 제거
    public void removeHashtag(Hashtag hashtag) {
        this.hashtags.removeIf(map -> map.getHashtag().equals(hashtag));
        hashtag.getPosts().removeIf(map -> map.getPost().equals(this));
    }
}

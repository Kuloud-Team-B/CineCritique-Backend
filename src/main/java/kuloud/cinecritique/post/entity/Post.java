package kuloud.cinecritique.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.cinema.entity.Cinema;
import kuloud.cinecritique.goods.entity.Goods;
import kuloud.cinecritique.post.dto.PostRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.*;


@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // 모든 필드를 포함하는 생성자를 자동 생성
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long Id;

    @NotBlank(message = "제목을 입력해주세요.")
    @Column(nullable = false, length = 15)
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Column(nullable = false, length = 200)
    private String content;

    // 게시글 좋아요 수
    @ColumnDefault("0")
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    // 댓글수
    @ColumnDefault("0")
    @Column(name = "comment_count",nullable = false)
    private Integer commentCount;

    // 이미지 url..?
    @Column(name = "post_img")
    private String postImg;

    // 평점
    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer rating;

    // 해시태그: 해시태그 관계를 PostHashtagMap을 통해 관리
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostHashtagMap> hashtags = new HashSet<>();

    // 조회수
    @ColumnDefault("0")
    @Column(name = "view_count",nullable = false)
    private Integer viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @Builder
    public Post(String title, String content, Member member, String postImg, Integer rating,
                Set<PostHashtagMap> postHashtagMaps, Movie movie, Cinema cinema, Goods goods) {
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
    public void updatePost(PostRequestDto postRequestDto, Movie movie, Cinema cinema, Goods goods,
                           Set<PostHashtagMap> hashtags) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.postImg = postRequestDto.getPostImg();
        this.rating = postRequestDto.getRating();

        /*
        this.movie = movie;
        this.cinema = cinema;
        this.goods = goods;
        */
        if (movie != null) {
            this.movie = movie;
        }
        if (cinema != null) {
            this.cinema = cinema;
        }
        if (goods != null) {
            this.goods = goods;
        }
        if (hashtags != null) {
            this.hashtags = hashtags;
        }
    }
    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateMovie(Movie movie){
        this.movie = movie;
    }

    public void updateCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public void updateGoods(Goods goods){
        this.goods = goods;
    }

    public void viewCountUp(Post post) {
        post.viewCount++;
    }

    // 해시태그 추가
    public void addHashtag(Hashtag hashtag) {
        PostHashtagMap postHashtagMap = new PostHashtagMap();
        this.hashtags.add(postHashtagMap);
        hashtag.getPosts().add(postHashtagMap);
    }


    // Utility method to update hashtags
    public void setHashtags(Set<Hashtag> newHashtags) {
        hashtags.clear();
        newHashtags.forEach(this::addHashtag);
    }
}

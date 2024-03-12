package kuloud.cinecritique.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.cinema.entity.Cinema;
import kuloud.cinecritique.goods.entity.Goods;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import kuloud.cinecritique.common.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    // 태그
    @Column(length = 40)
    private String hashtag;

    // 조회수
    @ColumnDefault("0")
    @Column(name = "view_count",nullable = false)
    private Integer viewCount;
    /*
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;
    */

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
    public Post(String title, String content, Member member, String postImg, int rating, String hashtag,
                Movie movie, Cinema cinema, Goods goods) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.postImg = postImg;
        this.rating = rating;
        this.hashtag = hashtag;
        this.movie = movie;
        this.cinema = cinema;
        this.goods = goods;
        // this.userId;
    }

    // 게시글 내용 업데이트를 위한 메소드
    public void update(String title, String content) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        }
        if (content != null && !content.isEmpty()) {
            this.content = content;
        }
    }
}

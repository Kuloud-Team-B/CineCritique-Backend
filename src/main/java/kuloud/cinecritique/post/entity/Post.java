package kuloud.cinecritique.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kuloud.cinecritique.cinema.repository.CinemaRepository;
import kuloud.cinecritique.comment.entity.Comment;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import kuloud.cinecritique.common.exception.CustomException;
import kuloud.cinecritique.common.exception.ErrorCode;
import kuloud.cinecritique.goods.repository.GoodsRepository;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.movie.repository.MovieRepository;
import kuloud.cinecritique.cinema.entity.Cinema;
import kuloud.cinecritique.goods.entity.Goods;
import kuloud.cinecritique.post.dto.PostRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.*;


//@AllArgsConstructor
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotBlank(message = "제목을 입력해주세요.")
    @Column(nullable = false, length = 15)
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Column(nullable = false, length = 200)
    private String content;

    @ColumnDefault("0")
    @Column(name = "like_count", nullable = false)
    private int likeCount;

    @ColumnDefault("0")
    @Column(name = "comment_count",nullable = false)
    private int commentCount;

    @Column(name = "post_img")
    private String imageURL;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int rating;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostHashtagMap> hashtags = new HashSet<>();

    @ColumnDefault("0")
    @Column(name = "view_count",nullable = false)
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    public Post(Member member, String title, String content, String imageURL,
                int rating, Integer commentCount, Integer viewCount, Integer likeCount,
                Movie movie, Cinema cinema, Goods goods, Set<PostHashtagMap> initialHashtags) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.imageURL = imageURL;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.movie = movie;
        this.cinema = cinema;
        this.goods = goods;
        initialHashtags.forEach(this::addHashtag);
    }

    public void addHashtag(PostHashtagMap postHashtagMap) {
        if (!this.hashtags.contains(postHashtagMap)) {
            this.hashtags.add(postHashtagMap);
            postHashtagMap.setPost(this);
            if (!postHashtagMap.getHashtag().getPosts().contains(postHashtagMap)) {
                postHashtagMap.getHashtag().addPost(postHashtagMap);
            }
        }
    }

    public void viewCountUp(Post post) {
        post.viewCount++;
    }

    public void incrementLikes() {
        this.likeCount++;
    }

    public void decrementLikes() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public Post updatePost(PostRequestDto postRequestDto,
                           MovieRepository movieRepository, CinemaRepository cinemaRepository, GoodsRepository goodsRepository) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.imageURL = postRequestDto.getImageURL();
        this.rating = postRequestDto.getRating();
        if (postRequestDto.getMovieId() != null) {
            this.movie = movieRepository.findById(postRequestDto.getMovieId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MOVIE));
        }

        if (postRequestDto.getCinemaId() != null) {
            this.cinema = cinemaRepository.findById(postRequestDto.getCinemaId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_CINEMA));
        }

        if (postRequestDto.getGoodsId() != null) {
            this.goods = goodsRepository.findById(postRequestDto.getGoodsId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_GOODS));
        }

        return this;
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateMovie(Movie movie) {
        this.movie = movie;
    }

    public void updateCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public void updateGoods(Goods goods) {
        this.goods = goods;
    }

    public void updateImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

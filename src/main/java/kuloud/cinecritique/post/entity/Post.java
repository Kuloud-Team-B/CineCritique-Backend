package kuloud.cinecritique.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kuloud.cinecritique.comment.entity.Comment;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.cinema.entity.Cinema;
import kuloud.cinecritique.goods.entity.Goods;
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

    public Post(Member member, String title, String content, String imageURL, int rating,
                Movie movie, Cinema cinema, Goods goods, Set<PostHashtagMap> initialHashtags) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.imageURL = imageURL;
        this.rating = rating;
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

    public void removeHashtag(Hashtag hashtag) {
        this.hashtags.removeIf(h -> h.getHashtag().equals(hashtag));
        hashtag.getPosts().removeIf(h -> h.getPost().equals(this));
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
    }

    public void incrementLikes() {
        this.likeCount++;
    }

    public void decrementLikes() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void incrementViews() {
        this.viewCount++;
    }

    public Post updatePost(String title, String content, String imageURL, int rating,
                           Movie movie, Cinema cinema, Goods goods) {
        this.title = title;
        this.content = content;
        this.imageURL = imageURL;
        this.rating = rating;
        this.movie = movie;
        this.cinema = cinema;
        this.goods = goods;

        return this;
    }

    public void cleanupBeforeDeletion() {
        removeAllHashtags();
        this.comments.forEach(comment -> comment.setPost(null));
        this.comments.clear();
    }

    private void removeAllHashtags() {
        hashtags.forEach(h -> {
            h.getHashtag().getPosts().remove(h);
            h.setPost(null);
            h.setHashtag(null);
        });
        hashtags.clear();
    }
}

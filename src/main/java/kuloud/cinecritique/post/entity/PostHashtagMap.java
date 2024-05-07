package kuloud.cinecritique.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post_hashtag_map")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHashtagMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_tag_map_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    // 정적 팩토리 메소드 추가
    public static PostHashtagMap create(Post post, Hashtag hashtag) {
        PostHashtagMap map = new PostHashtagMap();
        map.post = post;
        map.hashtag = hashtag;
        return map;
    }

    // Set method for Post
    public void setPost(Post post) {
        this.post = post;
        // Optionally, you might want to ensure consistency in bidirectional relationships:
        if (post != null) {
            post.getHashtags().add(this);
        }
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
        // Ensuring consistency
        if (hashtag != null) {
            hashtag.getPosts().add(this);
        }
    }
}

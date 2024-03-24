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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    public PostHashtagMap(Post post, Hashtag hashtag) {
        this.post = post;
        this.hashtag = hashtag;
    }

    // 게터만 제공
    public Long getId() {
        return id;
    }
    public Post getPost() {
        return post;
    }
    public Hashtag getHashtag() {
        return hashtag;
    }
}

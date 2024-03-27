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
        // 필요한 초기화 로직 추가 가능
        return map;
    }

}

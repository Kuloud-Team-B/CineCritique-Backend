package kuloud.cinecritique.post.entity;

import jakarta.persistence.*;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class Hashtag extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<PostHashtagMap> posts = new HashSet<>();

    public Hashtag(String tagName) {
        this.tagName = tagName;
    }

    public void addPost(PostHashtagMap postHashtagMap) {
        posts.add(postHashtagMap);
        postHashtagMap.setHashtag(this);
    }
}

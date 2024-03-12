package kuloud.cinecritique.post.entity;

import jakarta.persistence.*;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Hashtag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String hashtag;

    @ToString.Exclude
    @ManyToMany(mappedBy = "hashtags")
    private Set<Post> posts = new LinkedHashSet<>();
}

package kuloud.cinecritique.post.entity;

import jakarta.persistence.*;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hashtag extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostHashtagMap> posts = new HashSet<>();

    public Hashtag(String tagName) {
        // 문자열 태그 이름만을 매개변수로 받는 생성자 추가
            this.tagName = tagName;
    }
}

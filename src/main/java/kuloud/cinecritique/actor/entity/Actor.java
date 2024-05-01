package kuloud.cinecritique.actor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Actor extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "actor_id")
    private Long id;
    private String name;
    private String profileUrl;

    @Builder
    public Actor(String name, String profileUrl) {
        this.name = name;
        this.profileUrl = profileUrl;
    }
}

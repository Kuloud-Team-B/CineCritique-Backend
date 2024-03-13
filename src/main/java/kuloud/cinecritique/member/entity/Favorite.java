package kuloud.cinecritique.member.entity;

import jakarta.persistence.*;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import kuloud.cinecritique.movie.entity.Movie;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Favorite(Member member, Movie movie) {
        this.member = member;
        this.movie = movie;
    }
}

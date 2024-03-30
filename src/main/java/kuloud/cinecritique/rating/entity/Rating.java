package kuloud.cinecritique.rating.entity;

import jakarta.persistence.*;
import kuloud.cinecritique.member.entity.Member;
import kuloud.cinecritique.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int rating;

    public Rating(Movie movie, Member member, int rating) {
        this.movie = movie;
        this.member = member;
        this.rating = rating;
    }
}

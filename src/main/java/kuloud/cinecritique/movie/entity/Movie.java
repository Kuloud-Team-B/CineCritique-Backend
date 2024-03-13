package kuloud.cinecritique.movie.entity;

import kuloud.cinecritique.movie.entity.MovieGenre;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
// @Table(name = "movies")

/*
* JPA 엔티티 그래프(엔티티와 연관된 다른 엔티티들 로드 지시) 정의 어노테이션
*
* */
@NamedEntityGraph(name="Movies.withGenres", attributeNodes = {
        @NamedAttributeNode("genres")
})

public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")


    // 영화 정보 (영화 제목, 포스터 이미지 URL, 개봉일, 영화 개요)
    private String name;             // movie title
    private String titleImg;         // movie title image
    private LocalDate releasedDate;
    private String summary;
    private Long id;                 // movie id

    @Enumerated(value = EnumType.STRING)
    private MovieGrade grade;               // 영화 등급

    @Enumerated(value = EnumType.STRING)
    private MovieGenre genre;               // 영화 장르

    @ManyToMany
    private Set<MovieGenre> genres;             //  영화 장르 목록


    /**
     * MovieData 객체로 변환하는 메서드 추가
     * @return MovieData 객체
     */

    public MovieData toMovieData() {
        return MovieData.builder().movieId(this.id).tId(this.tId).rating(4.0).build();
    }
}

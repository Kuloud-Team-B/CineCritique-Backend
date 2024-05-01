package kuloud.cinecritique.movie.entity;

import kuloud.cinecritique.movie.entity.MovieGenre;
import jakarta.persistence.*;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
/*@NamedEntityGraph(name = "Movie.withGenres", attributeNodes = @NamedAttributeNode("genres"))
@Table(name = "movies")
* JPA 엔티티 그래프(엔티티와 연관된 다른 엔티티들 로드 지시) 정의 어노테이션
*
* */

public class Movie extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;                 // movie id

    // 영화 정보 (영화 제목, 포스터 이미지 URL, 개봉일, 영화 개요)
    @Column(nullable = false)
    private String name;             // movie title
    private String titleImg;         // movie title image
    private LocalDate releasedDate;  // 개봉일
    private String summary;          // 영화 개요


    @Enumerated(value = EnumType.STRING)
    private MovieGrade grade;        // 영화 등급

    @Enumerated(value = EnumType.STRING)
    private MovieGenre genre;       // 영화 단일 장르

    private double averageRating;   // 영화 평균 평점

    /*    @ManyToMany
        private Set<MovieGenre> genres;             //  영화 장르 목록*/



    public Movie(String name, String titleImg, LocalDate releasedDate, String summary, MovieGrade grade, MovieGenre genre) {
        this.name = name;
        this.titleImg = titleImg;
        this.releasedDate = releasedDate;
        this.summary = summary;
        this.grade = grade;
        this.genre = genre;
        averageRating = 0;
    }



    // 영화 정보 수정
    public void changeName(String name) {
        this.name = name;
    }

    public void changeTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public void changeReleasedDate(LocalDate releasedDate) {
        this.releasedDate = releasedDate;
    }

    public void changeSummary(String summary) {
        this.summary = summary;
    }

    public void changeGrade(MovieGrade grade) {
        this.grade = grade;
    }

    public void changeGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public void updateAverageRating(double rating) {
        this.averageRating = rating;
    }

    /**
     * MovieData 객체로 변환하는 메서드 추가
     * @return MovieData 객체
     */

/*    public MovieData toMovieData() {
        return MovieData.builder().movieId(this.id).tId(this.tId).rating(4.0).build();
    }*/
}

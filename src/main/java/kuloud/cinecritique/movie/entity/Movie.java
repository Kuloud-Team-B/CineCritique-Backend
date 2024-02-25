package kuloud.cinecritique.movie.entity;

import jakarta.persistence.*;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "movie_id")
    private Long id;
    private String name;
    private String titleImg;
    private LocalDate releasedDate;
    private String summary;
    @Enumerated(value = EnumType.STRING)
    private MovieGrade grade;
    @Enumerated(value = EnumType.STRING)
    private MovieGenre genre;

    public Movie(String name, String titleImg, LocalDate releasedDate, String summary, MovieGrade grade, MovieGenre genre) {
        this.name = name;
        this.titleImg = titleImg;
        this.releasedDate = releasedDate;
        this.summary = summary;
        this.grade = grade;
        this.genre = genre;
    }
}

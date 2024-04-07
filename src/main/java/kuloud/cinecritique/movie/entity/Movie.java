package kuloud.cinecritique.movie.entity;

import jakarta.persistence.*;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import kuloud.cinecritique.movie.dto.MovieUpdateDto;
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
    private double averageRating;

    public Movie(String name, String titleImg, LocalDate releasedDate, String summary, MovieGrade grade, MovieGenre genre) {
        this.name = name;
        this.titleImg = titleImg;
        this.releasedDate = releasedDate;
        this.summary = summary;
        this.grade = grade;
        this.genre = genre;
        averageRating = 0;
    }

    public void updateInfo(MovieUpdateDto dto) {
        if (dto.getName() != null) {
            name = dto.getName();
        }
        if (dto.getTitleImg() != null) {
            titleImg = dto.getTitleImg();
        }
        if (dto.getReleasedDate() != null) {
            releasedDate = dto.getReleasedDate();
        }
        if (dto.getSummary() != null) {
            summary = dto.getSummary();
        }
        if (dto.getGrade() != null) {
            grade = dto.getGrade();
        }
        if (dto.getGenre() != null) {
            genre = dto.getGenre();
        }
    }

    public void updateAverageRating(double rating) {
        this.averageRating = rating;
    }

}

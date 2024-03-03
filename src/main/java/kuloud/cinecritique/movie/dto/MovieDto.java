package kuloud.cinecritique.movie.dto;

import kuloud.cinecritique.movie.entity.Movie;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovieDto {
    private Long movieId;
    private String name;
    private String titleImg;
    private LocalDate releasedDate;
    private String summary;
    private String grade;
    private String genre;

    public MovieDto(Movie movie) {
        this.movieId = movie.getId();
        this.name = movie.getName();
        this.titleImg = movie.getTitleImg();
        this.releasedDate = movie.getReleasedDate();
        this.summary = movie.getSummary();
        this.grade = movie.getGrade().name();
        this.genre = movie.getGenre().name();
    }
}

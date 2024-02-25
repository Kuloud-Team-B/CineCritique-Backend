package kuloud.cinecritique.movie.dto;

import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.movie.entity.MovieGenre;
import kuloud.cinecritique.movie.entity.MovieGrade;

import java.time.LocalDate;

public class MovieDto {
    private String name;
    private String titleImg;
    private LocalDate releasedDate;
    private String summary;
    private String grade;
    private String genre;

    public MovieDto(Movie movie) {
        this.name = movie.getName();
        this.titleImg = movie.getTitleImg();
        this.releasedDate = movie.getReleasedDate();
        this.summary = movie.getSummary();
        this.grade = movie.getGrade().getName();
        this.genre = movie.getGenre().getName();
    }
}

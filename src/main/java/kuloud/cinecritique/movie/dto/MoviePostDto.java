package kuloud.cinecritique.movie.dto;

import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.movie.entity.MovieGenre;
import kuloud.cinecritique.movie.entity.MovieGrade;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MoviePostDto {
    private String name;
    private String titleImg;
    private LocalDate releasedDate;
    private String summary;
    private MovieGrade grade;
    private MovieGenre genre;

    public Movie toEntity() {
        return new Movie(name,titleImg,releasedDate,summary,grade,genre);
    }
}

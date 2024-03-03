package kuloud.cinecritique.movie.dto;

import jakarta.validation.constraints.NotBlank;
import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.movie.entity.MovieGenre;
import kuloud.cinecritique.movie.entity.MovieGrade;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MoviePostDto {
    @NotBlank
    private String name;
    @NotBlank
    private String titleImg;
    @NotBlank
    private LocalDate releasedDate;
    @NotBlank
    private String summary;
    private MovieGrade grade;
    private MovieGenre genre;

    public Movie toEntity() {
        return new Movie(name,titleImg,releasedDate,summary,grade,genre);
    }
}

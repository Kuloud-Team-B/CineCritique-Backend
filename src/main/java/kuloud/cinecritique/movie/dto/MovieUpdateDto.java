package kuloud.cinecritique.movie.dto;

import jakarta.validation.constraints.NotBlank;
import kuloud.cinecritique.movie.entity.MovieGenre;
import kuloud.cinecritique.movie.entity.MovieGrade;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovieUpdateDto {
    @NotBlank
    private String beforeName;
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
}

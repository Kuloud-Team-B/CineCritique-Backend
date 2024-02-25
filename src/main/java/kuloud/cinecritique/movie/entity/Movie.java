package kuloud.cinecritique.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Movie {
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


}

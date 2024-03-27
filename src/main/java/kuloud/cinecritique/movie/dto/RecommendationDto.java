package kuloud.cinecritique.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDto {
    private Long userId;
    private List<MovieData> pickedMovies;
}

@Data
@Builder
class MovieData {
    private Long movieId;
    private Double rating;
    // tId 필드가 빠져있음. 필요에 따라 추가 예정
}

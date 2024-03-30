package kuloud.cinecritique.member.dto;

import kuloud.cinecritique.member.entity.Favorite;
import kuloud.cinecritique.movie.dto.MovieDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteDto {
    private Long favoriteId;
    private MovieDto movie;

    public FavoriteDto(Favorite favorite) {
        favoriteId = favorite.getId();
        movie = new MovieDto(favorite.getMovie());
    }
}

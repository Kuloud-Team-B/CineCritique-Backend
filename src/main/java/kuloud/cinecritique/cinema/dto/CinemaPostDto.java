package kuloud.cinecritique.cinema.dto;

import kuloud.cinecritique.cinema.entity.Cinema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CinemaPostDto {
    private String name;
    private String location;
    private String company;

    public Cinema toEntity() {
        return new Cinema(name, location);
    }
}

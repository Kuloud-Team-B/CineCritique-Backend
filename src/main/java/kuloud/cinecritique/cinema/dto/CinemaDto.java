package kuloud.cinecritique.cinema.dto;

import kuloud.cinecritique.cinema.entity.Cinema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CinemaDto {
    private String name;
    private String location;
    private CompanyDto company;

    public CinemaDto(Cinema cinema) {
        name = cinema.getName();
        location = cinema.getLocation();
        company = new CompanyDto(cinema.getCompany());
    }
}

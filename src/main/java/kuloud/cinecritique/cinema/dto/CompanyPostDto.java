package kuloud.cinecritique.cinema.dto;

import kuloud.cinecritique.cinema.entity.Company;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyPostDto {
    private String name;
    private String iconImage;

    public Company toEntity() {
        return new Company(name, iconImage);
    }

}

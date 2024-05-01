package kuloud.cinecritique.cinema.dto;

import kuloud.cinecritique.cinema.entity.Company;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto {
    private String companyName;
    private String iconImage;

    public CompanyDto(Company company) {
        this.companyName = company.getName();
        this.iconImage = company.getIconImage();
    }
}

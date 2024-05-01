package kuloud.cinecritique.cinema.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kuloud.cinecritique.cinema.dto.CompanyUpdateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {
    @Id @GeneratedValue
    @Column(name = "company_id")
    private Long id;
    private String name;
    private String iconImage;

    public Company(String name, String iconImage) {
        this.name = name;
        this.iconImage = iconImage;
    }

    public void updateInfo(CompanyUpdateDto dto) {
        if (dto.getCompanyName() != null) {
            name = dto.getCompanyName();
        }
        if (dto.getIconImage() != null) {
            iconImage = dto.getIconImage();
        }
    }
}

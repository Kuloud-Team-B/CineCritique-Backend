package kuloud.cinecritique.cinema.entity;

import jakarta.persistence.*;
import kuloud.cinecritique.cinema.dto.CinemaUpdateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cinema {
    @Id @GeneratedValue
    @Column(name = "cinema_id")
    private Long id;
    private String name;
    private String location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public Cinema(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void updateInfo(CinemaUpdateDto dto) {
        if (dto.getName() != null) {
            name = dto.getName();
        }
        if (dto.getLocation() != null) {
            location = dto.getLocation();
        }
    }

}

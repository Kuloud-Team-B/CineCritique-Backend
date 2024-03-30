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
    private String location;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public Cinema(String location, String name) {
        this.location = location;
        this.name = name;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void updateInfo(CinemaUpdateDto dto) {
        if (!dto.getName().isBlank() && !dto.getName().equals(name)) {
            name = dto.getName();
        }
        if (!dto.getLocation().isBlank() && !dto.getLocation().equals(name)) {
            location = dto.getLocation();
        }
    }

}

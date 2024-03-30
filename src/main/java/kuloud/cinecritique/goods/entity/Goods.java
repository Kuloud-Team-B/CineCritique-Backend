package kuloud.cinecritique.goods.entity;

import jakarta.persistence.*;
import kuloud.cinecritique.goods.dto.GoodsUpdateDto;
import kuloud.cinecritique.movie.entity.Movie;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods {
    @Id @GeneratedValue
    @Column(name = "goods_id")
    private Long id;
    private String name;
    private int price;
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Goods(String name, int price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void updateInfo(GoodsUpdateDto dto) {
        if (!dto.getName().isBlank() && !dto.getName().equals(name)) {
            name = dto.getName();
        }
        if (dto.getPrice() > 0) {
            price = dto.getPrice();
        }
        if (!dto.getImage().isBlank() && !dto.getImage().equals(image)) {
            image = dto.getImage();
        }
    }
}

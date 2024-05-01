package kuloud.cinecritique.goods.dto;

import kuloud.cinecritique.goods.entity.Goods;
import kuloud.cinecritique.movie.dto.MovieDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsDto {
    private String name;
    private int price;
    private String image;
    private MovieDto movie;

    public GoodsDto(Goods goods) {
        name = goods.getName();
        price = goods.getPrice();
        image = goods.getImage();
        movie = new MovieDto(goods.getMovie());
    }
}

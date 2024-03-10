package kuloud.cinecritique.goods.dto;

import kuloud.cinecritique.goods.entity.Goods;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsPostDto {
    private String name;
    private int price;
    private String image;
    private String movieName;

    public Goods toEntity() {
        return new Goods(name, price, image);
    }
}

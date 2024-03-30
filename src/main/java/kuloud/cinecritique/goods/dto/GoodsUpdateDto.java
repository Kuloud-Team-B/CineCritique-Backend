package kuloud.cinecritique.goods.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsUpdateDto {
    private String beforeName;
    private String name;
    private int price;
    private String image;
    private String movieName;
}

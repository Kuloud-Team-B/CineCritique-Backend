package kuloud.cinecritique.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class PostRequestDto {

    @Getter
    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;

    @Getter
    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    @Getter
    private final String postImg;
    @Getter
    private final int rating;
    @Getter
    private final String hashtag;

    private final Long movie;
    private final Long cinema;
    private final Long goods;

    public PostRequestDto(String title, String content, String postImg, int rating, String hashtag, Long movie, Long cinema, Long goods) {
        this.title = title;
        this.content = content;
        this.postImg = postImg;
        this.rating = rating;
        this.hashtag = hashtag;
        this.movie = movie;
        this.cinema = cinema;
        this.goods = goods;
    }

    public Long getMovieId() {
        return movie;
    }
    public Long getCinemaId() {
        return cinema;
    }
    public Long getGoodsId() {
        return goods;
    }
}

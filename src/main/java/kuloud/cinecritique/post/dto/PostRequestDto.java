package kuloud.cinecritique.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PostRequestDto {

    private final String title;
    private final String content;
    private final String postImg;
    private final Integer rating;
    private final String hashtag;
    private final Long movie;
    private final Long cinema;
    private final Long goods;

    public PostRequestDto(String title, String content, String postImg, Integer rating, String hashtag, Long movie, Long cinema, Long goods) {
        this.title = title;
        this.content = content;
        this.postImg = postImg;
        this.rating = rating;
        this.hashtag = hashtag;
        this.movie = movie;
        this.cinema = cinema;
        this.goods = goods;
    }
}

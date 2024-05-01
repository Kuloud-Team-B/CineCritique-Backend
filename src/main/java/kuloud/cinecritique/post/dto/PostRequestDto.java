package kuloud.cinecritique.post.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PostRequestDto {

    private Long memberId;        // 작성자
    private String title;         // 제목
    private String content;       // 본문
    private String images;        // 첨부사진
    private Integer rating;       // 평점
    private Long hashtagMapId;    // 태그
    private Long movieId;         // 영화
    private Long cinemaId;        // 영화관
    private Long goodsId;         // 굿즈

    public PostRequestDto(Long memberId, String title, String content, String images, Integer rating,
                          Long hashtagMapId, Long movieId, Long cinemaId, Long goodsId) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.images = images;
        this.rating = rating;
        this.hashtagMapId = hashtagMapId;
        this.movieId = movieId;
        this.cinemaId = cinemaId;
        this.goodsId = goodsId;
    }
}

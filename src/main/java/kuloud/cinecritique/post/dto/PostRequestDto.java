package kuloud.cinecritique.post.dto;

import jakarta.validation.constraints.*;
import kuloud.cinecritique.post.entity.PostHashtagMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PostRequestDto {

    private Long memberId;
    @NotBlank(message = "Title is required")
    @Size(max = 15, message = "Title must not exceed 15 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(max = 200, message = "Content must not exceed 200 characters")
    private String content;
    private String imageURL;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer rating;
    private Set<PostHashtagMap> hashtags;
    private Long movieId;
    private Long cinemaId;
    private Long goodsId;

    public PostRequestDto(Long memberId, String title, String content, String imageURL, Integer rating,
                          Set<PostHashtagMap> hashtags, Long movieId, Long cinemaId, Long goodsId) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.imageURL = imageURL;
        this.rating = rating;
        this.hashtags = hashtags;
        this.movieId = movieId;
        this.cinemaId = cinemaId;
        this.goodsId = goodsId;
    }
}

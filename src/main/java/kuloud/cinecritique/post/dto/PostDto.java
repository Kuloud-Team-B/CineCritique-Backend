package kuloud.cinecritique.post.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostDto {

    // Getters
    private final Long id;
    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;
    private final Long memberId;
    private final String authorName;
    private final String postImg;
    private final int rating;
    private final String hashtag;

    public PostDto(Long id, String title, String content, Long memberId, String authorName, String postImg, int rating, String hashtag) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.authorName = authorName;
        this.postImg = postImg;
        this.rating = rating;
        this.hashtag = hashtag;
    }

}

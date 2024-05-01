package kuloud.cinecritique.post.dto;

import jakarta.validation.constraints.NotBlank;

public class PostRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    private final String postImg;
    private final int rating;
    private final String hashtag;

    public PostRequestDto(String title, String content, String postImg, int rating, String hashtag) {
        this.title = title;
        this.content = content;
        this.postImg = postImg;
        this.rating = rating;
        this.hashtag = hashtag;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPostImg() {
        return postImg;
    }

    public int getRating() {
        return rating;
    }

    public String getHashtag() {
        return hashtag;
    }

}

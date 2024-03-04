package kuloud.cinecritique.post.dto;

public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final Long memberId;
    private final String authorNickname;
    private final String postImg;
    private final int rating;
    private final String hashtag;

    public PostResponseDto(Long id, String title, String content, Long memberId, String authorNickname, String postImg, int rating, String hashtag) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.authorNickname = authorNickname;
        this.postImg = postImg;
        this.rating = rating;
        this.hashtag = hashtag;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getAuthorNickname() {
        return authorNickname;
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

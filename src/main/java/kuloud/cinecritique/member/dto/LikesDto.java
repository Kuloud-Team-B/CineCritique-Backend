package kuloud.cinecritique.member.dto;

import kuloud.cinecritique.member.entity.Likes;
import kuloud.cinecritique.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesDto {
    private Long likesId;
    private Post post;

    public LikesDto(Likes likes) {
        likesId = likes.getId();
        post = likes.getPost();
    }
}

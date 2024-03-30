package kuloud.cinecritique.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeRequestDto {
    private final Long postId; // 게시글 ID
}

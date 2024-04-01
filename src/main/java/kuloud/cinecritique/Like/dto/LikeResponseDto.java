package kuloud.cinecritique.Like.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class LikeResponseDto {
    private String message;
    private boolean likeStatus;

    public LikeResponseDto(String message, boolean likeStatus) {
        this.message = message;
        this.likeStatus = likeStatus;
    }
}

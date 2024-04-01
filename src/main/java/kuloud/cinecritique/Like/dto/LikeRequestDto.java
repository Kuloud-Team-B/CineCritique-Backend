package kuloud.cinecritique.Like.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeRequestDto {
    private Long postId;
    private Long memberId;

    public LikeRequestDto(Long postId, Long memberId) {
        this.postId = postId;
        this.memberId = memberId;
    }
}

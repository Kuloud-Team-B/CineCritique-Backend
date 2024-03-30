package kuloud.cinecritique.rating.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RatingRequestDto {
    private final Long movieId;
    private final int rating;

    // Lombok @Builder를 사용하여 객체를 생성할 수 있도록 합니다.
}

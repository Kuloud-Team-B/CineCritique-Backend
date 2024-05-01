package kuloud.cinecritique.post;

import kuloud.cinecritique.movie.entity.Movie;
import kuloud.cinecritique.post.dto.PostResponseDto;
import org.mapstruct.Mapping;

import java.util.List;

public interface GenericMapper<D, E> {

    D toDto(E e);

    E toEntity(D d);

    List<PostResponseDto> toDtoList(List<E> entityList);

    List<E> toEntityList(List<D> dtoList);

    @Mapping(source = "movie.id", target = "movie") // Movie 객체의 ID를 Long 필드에 매핑
    @Mapping(source = "cinema.id", target = "cinema") // Cinema 객체의 ID를 Long 필드에 매핑
    @Mapping(source = "goods.id", target = "goods") // Goods 객체의 ID를 Long 필드에 매핑
    @Mapping(source = "member.id", target = "memberId") // Member 객체의 ID를 Long 필드에 매핑
    @Mapping(source = "hashtags", target = "hashtag") // Hashtag 객체를 처리할 방법 명시(아래 설명 참조)

    // MapStruct가 Movie 객체를 Long 타입으로 매핑하는 방법을 정의
    Long map(Movie movie);
}

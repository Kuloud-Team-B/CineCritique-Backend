package kuloud.cinecritique.post.mapper;

import kuloud.cinecritique.post.dto.PostResponseDto;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.GenericMapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PostResponseMapper extends GenericMapper<PostResponseDto, Post> {
    PostResponseMapper INSTANCE = Mappers.getMapper(PostResponseMapper.class);
    @Mappings({
            @Mapping(source = "member.id", target = "memberId"),
            @Mapping(source = "movie.id", target = "movieId"),
            @Mapping(source = "cinema.id", target = "cinemaId"),
            @Mapping(source = "goods.id", target = "goodsId")
    })
    PostResponseDto toDto(Post post);

    @Mappings({
            @Mapping(target = "member.id", source = "memberId"),
            @Mapping(target = "movie.id", source = "movieId"),
            @Mapping(target = "cinema.id", source = "cinemaId"),
            @Mapping(target = "goods.id", source = "goodsId")
            // Make sure to handle the initialization of the collection fields like comments and hashtags
    })
    Post toEntity(PostResponseDto dto);
}

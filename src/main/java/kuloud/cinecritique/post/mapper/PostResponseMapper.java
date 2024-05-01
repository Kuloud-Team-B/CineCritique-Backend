package kuloud.cinecritique.post.mapper;

import kuloud.cinecritique.post.dto.PostResponseDto;
import kuloud.cinecritique.post.entity.Post;
import kuloud.cinecritique.post.GenericMapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostResponseMapper extends GenericMapper<PostResponseDto, Post> {
    PostResponseMapper INSTANCE = Mappers.getMapper(PostResponseMapper.class);
}

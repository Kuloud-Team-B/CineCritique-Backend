package kuloud.cinecritique.post.mapper;

import kuloud.cinecritique.post.GenericMapper;
import kuloud.cinecritique.post.dto.PostRequestDto;
import kuloud.cinecritique.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostResponseMapper extends GenericMapper<PostRequestDto, Post> {
    PostResponseMapper INSTANCE = Mappers.getMapper(PostResponseMapper.class);
}

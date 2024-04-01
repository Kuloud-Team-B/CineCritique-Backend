package kuloud.cinecritique.post.mapper;

import kuloud.cinecritique.post.GenericMapper;
import kuloud.cinecritique.post.dto.PostRequestDto;
import kuloud.cinecritique.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostRequestMapper extends GenericMapper<PostRequestDto, Post> {
    PostRequestMapper INSTANCE = Mappers.getMapper(PostRequestMapper.class);
}

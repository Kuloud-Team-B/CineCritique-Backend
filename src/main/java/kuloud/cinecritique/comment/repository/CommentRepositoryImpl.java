package kuloud.cinecritique.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kuloud.cinecritique.comment.dto.CommentResponseDto;
import kuloud.cinecritique.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

import static kuloud.cinecritique.comment.dto.CommentResponseDto.convertCommentToDto;
import static kuloud.cinecritique.comment.entity.QComment.comment;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentResponseDto> findByPostId(Long id) {

        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.post.id.eq(id))
                .orderBy(comment.parent.id.asc().nullsFirst(),
                        comment.createdDate.asc())
                .fetch();

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> commentDtoHashMap = new HashMap<>();

        comments.forEach(c -> {
            CommentResponseDto commentResponseDto = convertCommentToDto(c);
            commentDtoHashMap.put(commentResponseDto.getId(), commentResponseDto);
            if (c.getParent() != null) commentDtoHashMap.get(c.getParent().getId()).getChildren().add(commentResponseDto);
            else commentResponseDtoList.add(commentResponseDto);
        });
        return commentResponseDtoList;
    }

    @Override
    public Optional<Comment> findCommentByIdWithParent(Long id) {

        Comment selectedComment = queryFactory.select(comment)
                .from(comment)
                .leftJoin(comment.parent).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(selectedComment);
    }
}

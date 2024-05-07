package kuloud.cinecritique.comment.repository;

import kuloud.cinecritique.comment.dto.CommentResponseDto;
import kuloud.cinecritique.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentCustomRepository {

    List<CommentResponseDto> findByPostId(Long id);

    Optional<Comment> findCommentByIdWithParent(Long id);
}

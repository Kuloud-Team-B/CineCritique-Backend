package kuloud.cinecritique.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;    // 댓글 ID
    private String content; // 댓글 내용
    private String authorNickname; // 댓글 작성자 닉네임
    private Long postId; // 댓글이 속한 게시글 ID
}

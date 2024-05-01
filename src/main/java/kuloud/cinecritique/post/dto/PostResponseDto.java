package kuloud.cinecritique.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kuloud.cinecritique.comment.dto.CommentResponseDto;
import kuloud.cinecritique.member.dto.MemberDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {
    private Long id;
    private MemberDto member;           // 작성자
    private String title;               // 제목
    private String hashTag;             // 태그
    private String content;             // 본문
    private Integer rating;             // 평점
    private Integer likeCount;          // 좋아요 수
    private Integer viewCount;          // 조회수
    private Integer commentCount;       // 댓글 수
    private Long movieId;               // 영화
    private Long cinemaId;              // 영화관
    private Long goodsId;               // 굿즈
    private List<CommentResponseDto> comments; // 댓글 목록
    private LocalDateTime createdAt;    // 작성시간
    private LocalDateTime updatedAt;    // 수정시간

    @Builder
    public PostResponseDto(Long id, String title, MemberDto member, String hashTag, String content, Integer likeCount,
                           Integer viewCount, Integer rating, Integer commentCount, Long movieId, Long cinemaId, Long goodsId,
                           List<CommentResponseDto> comments, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.hashTag = hashTag;
        this.content = content;
        this.rating = rating;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.movieId = movieId;
        this.cinemaId = cinemaId;
        this.goodsId = goodsId;
        this.comments = comments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}

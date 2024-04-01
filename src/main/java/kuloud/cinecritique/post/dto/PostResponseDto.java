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
    private String title;
    private String hashTag;
    private String content;
    private Integer likeCount;
    private Integer viewCount;
    private Integer commentCount;
    private MemberDto memberDto;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponseDto> commentResponseDto;

    @Builder
    public PostResponseDto(Long id, String title, String hashTag, String content, Integer likeCount, Integer viewCount, 
                           Integer commentCount, MemberDto memberDto, List<CommentResponseDto> commentResponseDto,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.hashTag = hashTag;
        this.content = content;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.memberDto = memberDto;
        this.commentResponseDto = commentResponseDto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Builder
    public PostResponseDto(Long id, String title, String hashTag, String content, Integer likeCount,
                            Integer viewCount, Integer commentCount, MemberDto memberDto) {
        this.id = id;
        this.title = title;
        this.hashTag = hashTag;
        this.content = content;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.memberDto = memberDto;
    }
}
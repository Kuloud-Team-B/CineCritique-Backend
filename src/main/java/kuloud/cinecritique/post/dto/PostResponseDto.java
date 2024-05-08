package kuloud.cinecritique.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kuloud.cinecritique.comment.dto.CommentResponseDto;
import kuloud.cinecritique.member.dto.MemberDto;
import kuloud.cinecritique.post.entity.PostHashtagMap;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {
    private Long id;
    private MemberDto member;

    @NotBlank(message = "Title is required")
    @Size(max = 15, message = "Title must not exceed 15 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(max = 200, message = "Content must not exceed 200 characters")
    private String content;
    private Integer rating;
    private Set<PostHashtagMap> hashtags;
    private String imageURL;
    private Long movie;
    private Long cinema;
    private Long goods;
    private List<CommentResponseDto> commentResponseDtoList;
    private Integer likeCount;
    private Integer viewCount;
    private Integer commentCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;

    @Builder
    public PostResponseDto(Long id, String title, MemberDto member, Set<PostHashtagMap> hashtags, String imageURL, String content, Integer likeCount,
                           Integer viewCount, Integer rating, Integer commentCount, Long movie, Long cinema, Long goods,
                           List<CommentResponseDto> commentResponseDtoList, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.hashtags = hashtags;
        this.imageURL = imageURL;
        this.content = content;
        this.rating = rating;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.movie = movie;
        this.cinema = cinema;
        this.goods = goods;
        this.commentResponseDtoList = commentResponseDtoList;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

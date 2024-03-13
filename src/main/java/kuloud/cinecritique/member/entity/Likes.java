package kuloud.cinecritique.member.entity;

import jakarta.persistence.*;
import kuloud.cinecritique.common.entity.BaseTimeEntity;
import kuloud.cinecritique.member.dto.LikesDto;
import kuloud.cinecritique.post.entity.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "likes_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Likes(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

}

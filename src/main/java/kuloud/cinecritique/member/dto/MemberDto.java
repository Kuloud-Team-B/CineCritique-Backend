package kuloud.cinecritique.member.dto;


import kuloud.cinecritique.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String nickname;
    private String profileImg;

    public MemberDto(Member member) {
        this.nickname = member.getNickname();
        this.profileImg = member.getProfileImage();
    }
}

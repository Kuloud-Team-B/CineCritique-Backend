package kuloud.cinecritique.member.dto;


import kuloud.cinecritique.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyPageDto {
    private String nickname;
    private String email;
    private String profileImg;
    private LocalDateTime signUpDate;
    private LocalDateTime lastModifiedDate;

    public MyPageDto(Member member) {
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.profileImg = member.getProfileImage();
        this.signUpDate = member.getCreatedDate();
        this.lastModifiedDate = member.getLastModifiedDate();
    }
}

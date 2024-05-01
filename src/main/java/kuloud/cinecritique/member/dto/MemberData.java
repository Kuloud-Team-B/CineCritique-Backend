package kuloud.cinecritique.member.dto;

import kuloud.cinecritique.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberData {
    private String memberId;
    private String email;
    private String role;

    public MemberData(Member member) {
        this.memberId = member.getId().toString();
        this.email = member.getEmail();
        role = "ROLE_USER";
    }
}

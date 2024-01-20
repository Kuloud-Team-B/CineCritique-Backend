package kuloud.cinecritique.member.dto;


import kuloud.cinecritique.member.entity.Member;

public record MemberDto(String nickname) {
    public static MemberDto createWith(Member member) {
        return new MemberDto(member.getNickname());
    }
}

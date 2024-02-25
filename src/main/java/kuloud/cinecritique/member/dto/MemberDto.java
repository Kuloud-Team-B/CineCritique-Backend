package kuloud.cinecritique.member.dto;


import kuloud.cinecritique.member.entity.Member;

public record MemberDto(String nickname, String profileImage) {
    public static MemberDto createWith(Member member) {
        return new MemberDto(member.getNickname(), member.getProfileImage());
    }
}

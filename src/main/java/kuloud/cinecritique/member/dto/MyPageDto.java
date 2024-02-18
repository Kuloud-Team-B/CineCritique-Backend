package kuloud.cinecritique.member.dto;


import kuloud.cinecritique.member.entity.Member;

public record MyPageDto(String nickname, String email, String profileImage) {
    public static MyPageDto createWith(Member member) {
        return new MyPageDto(member.getNickname(), member.getEmail(), member.getProfileImage());
    }
}

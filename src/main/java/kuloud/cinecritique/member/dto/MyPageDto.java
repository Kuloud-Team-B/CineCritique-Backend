package kuloud.cinecritique.member.dto;


import kuloud.cinecritique.member.entity.Member;

public record MyPageDto(String nickname, String phone) {
    public static MyPageDto createWith(Member member) {
        return new MyPageDto(member.getNickname(), member.getPhoneNumber());
    }
}

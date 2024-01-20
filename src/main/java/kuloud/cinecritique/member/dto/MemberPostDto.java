package kuloud.cinecritique.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kuloud.cinecritique.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
public class MemberPostDto {
    @NotBlank
    private String nickname;
    @Size(min = 11, max = 11)
    private String phone;
    @Size(min = 8, max = 32)
    private String password;

    public Member toEntityWithEncoder(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phone)
                .password(passwordEncoder.encode(password))
                .build();
    }
}

package kuloud.cinecritique.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kuloud.cinecritique.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class MemberPostDto {
    @NotBlank
    private String nickname;
    @Email
    private String email;
    @Size(min = 8, max = 32)
    private String password;
    private String profileImage;

    public Member toEntityWithEncoder(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .password(passwordEncoder.encode(password))
                .profileImage(profileImage)
                .build();
    }

}

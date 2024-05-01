package kuloud.cinecritique.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kuloud.cinecritique.member.entity.Admin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class AdminPostDto {
    @NotBlank
    private String name;
    @Size(min = 8, max = 32)
    private String password;

    public Admin toEntityWithEncoder(PasswordEncoder passwordEncoder) {
        return Admin.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
